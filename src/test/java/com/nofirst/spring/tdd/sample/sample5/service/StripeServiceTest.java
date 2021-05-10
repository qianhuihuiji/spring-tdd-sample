package com.nofirst.spring.tdd.sample.sample5.service;

import com.nofirst.spring.tdd.sample.sample5.domain.CardPaymentCharge;
import com.nofirst.spring.tdd.sample.sample5.domain.Currency;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class StripeServiceTest {

    private StripeService stripeService;

    @Mock
    private StripeApi stripeApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        stripeService = new StripeService(stripeApi);
    }

    @Test
    void it_should_charge_card() throws StripeException {
        // Given
        String cardSource = "0x0x0x";
        BigDecimal amount = new BigDecimal("10.00");
        Currency currency = Currency.USD;
        String description = "test";

        // Successful charge
        Charge charge = new Charge();
        charge.setPaid(true);
        given(stripeApi.create(anyMap(), any())).willReturn(charge);

        // When
        CardPaymentCharge cardPaymentCharge = stripeService.chargeCard(cardSource, amount, currency, description);

        // Then
        // 模拟方法的请求参数
        ArgumentCaptor<Map<String, Object>> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<RequestOptions> optionsArgumentCaptor = ArgumentCaptor.forClass(RequestOptions.class);

        // Captor requestMap and options
        then(stripeApi).should().create(mapArgumentCaptor.capture(), optionsArgumentCaptor.capture());

        // Assert on requestMap
        Map<String, Object> requestMap = mapArgumentCaptor.getValue();

        assertThat(requestMap.keySet()).hasSize(4);

        assertThat(requestMap.get("amount")).isEqualTo(amount);
        assertThat(requestMap.get("currency")).isEqualTo(currency);
        assertThat(requestMap.get("source")).isEqualTo(cardSource);
        assertThat(requestMap.get("description")).isEqualTo(description);

        // Assert on options
        RequestOptions options = optionsArgumentCaptor.getValue();

        assertThat(options).isNotNull();

        // card is debited successfully
        assertThat(cardPaymentCharge).isNotNull();
        assertThat(cardPaymentCharge.isCardDebited()).isTrue();
    }

    @Test
    void it_should_not_charge_when_api_throws_exception() throws StripeException {
        // Given
        String cardSource = "0x0x0x";
        BigDecimal amount = new BigDecimal("10.00");
        Currency currency = Currency.USD;
        String description = "Test";

        // Throw exception when stripe api is called
        StripeException stripeException = mock(StripeException.class);
        doThrow(stripeException).when(stripeApi).create(anyMap(), any());

        // When
        // Then
        assertThatThrownBy(() -> stripeService.chargeCard(cardSource, amount, currency, description))
                .isInstanceOf(IllegalStateException.class)
                .hasRootCause(stripeException)
                .hasMessageContaining("Cannot make stripe charge");
    }
}
