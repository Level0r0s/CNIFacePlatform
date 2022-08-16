package cn.abellee.cniface.platform.milvus;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.grpc.DescribeCollectionResponse;
import io.milvus.grpc.GetCollectionStatisticsResponse;
import io.milvus.grpc.ShowCollectionsResponse;
import io.milvus.param.IndexType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.*;
import io.milvus.response.DescCollResponseWrapper;
import io.milvus.response.GetCollStatResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author abel
 * @date 2022/8/14 12:59 PM
 */
@Service
public class MilvusClientService {

    private final MilvusServiceClient milvusClient;

    private static final String COLLECTION_NAME = "TEST";
    private static final String ID_FIELD = "id";
    private static final String VECTOR_FIELD = "feature";
    private static final String FACE_URI_FIELD = "face_uri";
    private static final Integer VECTOR_DIM = 512;
    private static final Integer DEFAULT_FACE_VECTOR_DIM = 512;
    private static final Long DEFAULT_CREATE_COLLECTION_TIME_OUT = 2000L;

    private static final String INDEX_NAME = "userFaceIndex";

    private static final IndexType INDEX_TYPE = IndexType.IVF_FLAT;
    private static final String INDEX_PARAM = "{\"nlist\":128}";

    private static final Integer SEARCH_K = 5;
    private static final String SEARCH_PARAM = "{\"nprobe\":10}";

    public MilvusClientService(MilvusServiceClient milvusClient) {
        this.milvusClient = milvusClient;
    }


    private void handleResponseStatus(R<?> r) {
        if (r.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException(r.getMessage());
        }
    }

    public R<RpcStatus> createFaceCollection(String name) {
        return createFaceCollection(name, DEFAULT_FACE_VECTOR_DIM, DEFAULT_CREATE_COLLECTION_TIME_OUT);
    }

    public R<RpcStatus> createFaceCollection(String name, int dim, long timeoutMilliseconds) {
        FieldType fieldType1 = FieldType.newBuilder()
                .withName(ID_FIELD)
                .withDescription("user identification")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(true)
                .build();

        FieldType fieldType2 = FieldType.newBuilder()
                .withName(VECTOR_FIELD)
                .withDescription("face embedding")
                .withDataType(DataType.FloatVector)
                .withDimension(dim)
                .build();

        FieldType fieldType3 = FieldType.newBuilder()
                .withName(VECTOR_FIELD)
                .withDescription("face embedding")
                .withDataType(DataType.FloatVector)
                .withDimension(dim)
                .build();


        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(name)
                .withDescription("face repo")
//                .withShardsNum(2)
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .build();
        R<RpcStatus> response = milvusClient.withTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
                .createCollection(createCollectionReq);
        handleResponseStatus(response);
        return response;
    }

    public R<RpcStatus> dropCollection(String name) {
        System.out.println("========== dropCollection() ==========");
        R<RpcStatus> response = milvusClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        System.out.println(response);
        return response;
    }

    public boolean hasCollection(String name) {
        System.out.println("========== hasCollection() ==========");
        R<Boolean> response = milvusClient.hasCollection(HasCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response.getData();
    }

    public R<RpcStatus> loadCollection(String name) {
        System.out.println("========== loadCollection() ==========");
        R<RpcStatus> response = milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<RpcStatus> releaseCollection(String name) {
        System.out.println("========== releaseCollection() ==========");
        R<RpcStatus> response = milvusClient.releaseCollection(ReleaseCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<DescribeCollectionResponse> describeCollection(String name) {
        System.out.println("========== describeCollection() ==========");
        R<DescribeCollectionResponse> response = milvusClient.describeCollection(DescribeCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        DescCollResponseWrapper wrapper = new DescCollResponseWrapper(response.getData());
        System.out.println(wrapper.toString());
        return response;
    }

    public R<GetCollectionStatisticsResponse> getCollectionStatistics(String name) {
        System.out.println("========== getCollectionStatistics() ==========");
        R<GetCollectionStatisticsResponse> response = milvusClient.getCollectionStatistics(
                GetCollectionStatisticsParam.newBuilder()
                        .withCollectionName(name)
                        .build());
        handleResponseStatus(response);
        GetCollStatResponseWrapper wrapper = new GetCollStatResponseWrapper(response.getData());
        System.out.println("Collection row count: " + wrapper.getRowCount());
        return response;
    }

    public R<ShowCollectionsResponse> showCollections() {
        System.out.println("========== showCollections() ==========");
        R<ShowCollectionsResponse> response = milvusClient.showCollections(ShowCollectionsParam.newBuilder()
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }
}
