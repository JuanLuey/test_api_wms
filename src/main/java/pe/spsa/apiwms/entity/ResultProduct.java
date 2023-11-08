package pe.spsa.apiwms.entity;

import lombok.Data;

@Data
public class ResultProduct {
    private String item_alternate_code;
    private String description;
    private String barcode;
    private String short_descr;
    private String unit_cost;
    private String unit_length;
    private String unit_width;
    private String unit_height;
    private String unit_weight;
    private String unit_volume;
}
