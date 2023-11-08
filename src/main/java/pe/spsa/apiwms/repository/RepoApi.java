package pe.spsa.apiwms.repository;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pe.spsa.apiwms.entity.ResultDetail;
import pe.spsa.apiwms.entity.ResultDtlSet;
import pe.spsa.apiwms.entity.ResultHeader;
import pe.spsa.apiwms.entity.ResultItem;
import pe.spsa.apiwms.entity.ResultProduct;

@Repository
public class RepoApi {

        @Value("${api.url}")
        public String api_url;

        @Value("${api.password}")
        public String api_password;

        private final Logger logger = LoggerFactory.getLogger(RepoApi.class);

        public void getHead(String shipment_nbr, String org) throws IOException {

                OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();

                ObjectMapper objectMapper = new ObjectMapper();

                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                Request request = new Request.Builder()
                                .url(api_url + "/ib_shipment/?facility_id__code="
                                                + org + "&shipment_nbr="
                                                + shipment_nbr)
                                .method("GET", null)
                                .addHeader("Authorization", "Basic " + api_password)
                                .build();

                Response response = client.newCall(request).execute();

                String response_string = response.body().string();

                int responsehead = response.code();

                logger.info(" Response Code Head:  {}", responsehead);

                if (responsehead == 200) {

                        ResultHeader resultHeader = objectMapper.readValue(response_string, ResultHeader.class);

                        logger.info(" Response getId: {}", resultHeader.getResults().get(0).getId());
                        logger.info(" Response getResult_count: {}", resultHeader.getResult_count());

                        getDetail(resultHeader, org);
                } else if (responsehead == 404) {
                        logger.info("No data found header");
                } else {
                        logger.info(" other error response:  {}", responsehead);
                }

        }

        public void getDetail(ResultHeader resultHeader, String org) throws IOException {

                OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();

                int ib_shipment_id = resultHeader.getResults().get(0).getId();
                logger.info(" ib_shipment_id:  {}", ib_shipment_id);
                
                ResultDtlSet resultURLDet = resultHeader.getResults().get(0).getIb_shipment_dtl_set();

                ObjectMapper objectMapper = new ObjectMapper();

                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                Request request = new Request.Builder()
                                .url(resultURLDet.getUrl())
                                .method("GET", null)
                                .addHeader("Authorization", "Basic " + api_password)
                                .build();

                Response response = client.newCall(request).execute();

                String response_string = response.body().string();

                logger.info(" Response Code Details:  {}", response.code());

                ResultDetail resultDetail = objectMapper.readValue(response_string, ResultDetail.class);

                int number_detail = resultDetail.getResult_count();

                logger.info(" Response Detail getResult_count: {}", number_detail);

                for (int i = 0; i < number_detail; i++) {
                        logger.info(" Response Detail getContainer_nbr: {}",
                                        resultDetail.getResults().get(i).getContainer_nbr() +
                                                        " getid: " + resultDetail.getResults().get(i).getId() +
                                                        " getPallet_nbr: "
                                                        + resultDetail.getResults().get(i).getPallet_nbr() +
                                                        " getShipped_qty: "
                                                        + resultDetail.getResults().get(i).getShipped_qty());

                        ResultItem resultItem = resultDetail.getResults().get(i).getItem_id();

                        getProduct(resultItem);

                }

        }

        public void getProduct(ResultItem resultItem) throws IOException {

                OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();

                ObjectMapper objectMapper = new ObjectMapper();

                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                Request request = new Request.Builder()
                                .url(resultItem.getUrl())
                                .method("GET", null)
                                .addHeader("Authorization", "Basic " + api_password)
                                .build();

                Response response = client.newCall(request).execute();

                String response_string = response.body().string();

                // logger.info(" Response Code Details {}", response.code());

                ResultProduct resultProduct = objectMapper.readValue(response_string, ResultProduct.class);

                logger.info(" Response Product alternate_code: {}", resultProduct.getItem_alternate_code());
                logger.info(" Response Product getBarcode: {}", resultProduct.getBarcode());
                logger.info(" Response Product getShort_descr: {}", resultProduct.getShort_descr());
                logger.info(" Response Product getDescription: {}", resultProduct.getDescription());

        }

}
