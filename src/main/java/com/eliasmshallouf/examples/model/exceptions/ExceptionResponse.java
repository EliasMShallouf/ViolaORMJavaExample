package com.eliasmshallouf.examples.model.exceptions;

import java.io.Serializable;

public record ExceptionResponse(String cause) implements Serializable {

}
