package br.com.wl.errorhandler;

import lombok.Data;

import java.util.List;

@Data
public class VndError {
    private List<ErrorMessage> errors;
    private String uri;
    private Long timestamp;
}
