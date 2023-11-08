package pe.spsa.apiwms.entity;

import java.util.List;

import lombok.Data;

@Data
public class ResultDetail {
    private int result_count;
    private List<ResultDetailArray> results;
}
