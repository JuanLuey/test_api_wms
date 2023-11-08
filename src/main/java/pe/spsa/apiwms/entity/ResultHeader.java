package pe.spsa.apiwms.entity;

import java.util.List;

import lombok.Data;

@Data
public class ResultHeader {
    private int result_count;
    private List<ResultHeadArray> results;
}
