package com.github.gmessiasc.hermes4j.core.handlers;

import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import java.util.function.Function;

public interface HttpHandler extends Function<HttpRequest, HttpResponse> {
}
