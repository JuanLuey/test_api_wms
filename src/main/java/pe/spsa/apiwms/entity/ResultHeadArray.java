package pe.spsa.apiwms.entity;
import lombok.Data;

@Data
public class ResultHeadArray {
    private int id;
    private ResultDtlSet ib_shipment_dtl_set;
    private ResultFacility facility_id;
    private ResultCompany company_id;
    private ResultShpType shipment_type_id;
    private ResultOrigin origin_facility_id;
}
