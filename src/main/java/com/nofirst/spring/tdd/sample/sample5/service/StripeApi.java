package com.nofirst.spring.tdd.sample.sample5.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class StripeApi {

    public Charge create(Map<String, Object> requestMap, RequestOptions options) throws StripeException {
        return Charge.create(requestMap, options);
    }
}
