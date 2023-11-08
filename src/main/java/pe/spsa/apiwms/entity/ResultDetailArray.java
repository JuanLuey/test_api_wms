package pe.spsa.apiwms.entity;

import lombok.Data;

@Data
public class ResultDetailArray {
    private int id;
    private String container_nbr;
    private String shipped_qty;
    private String received_qty;
    private String pallet_nbr;
    private ResultItem item_id;
    private String priority_date;
    private String original_shipto;  
    private String expiry_date;
    
}
