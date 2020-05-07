package net.softgal.feeed.domain;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Price {
    private String instrument;
    private String bid;
    private String ask;
    private String time;
}
