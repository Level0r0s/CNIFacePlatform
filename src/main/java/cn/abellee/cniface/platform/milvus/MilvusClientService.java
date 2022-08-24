package cn.abellee.cniface.platform.milvus;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.*;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.*;
import io.milvus.param.control.ManualCompactParam;
import io.milvus.param.dml.DeleteParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.QueryParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.*;
import io.milvus.param.partition.*;
import io.milvus.response.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author abel
 * @date 2022/8/14 12:59 PM
 */
@Service
public class MilvusClientService {

    private final MilvusServiceClient milvusClient;

    private static final String ID_FIELD = "id";
    private static final String VECTOR_FIELD = "feature";
    private static final String NAME_FIELD = "name";
    private static final String PERSON_ID = "person_id";
    private static final String FACE_URI_FIELD = "face_uri";
    private static final Integer DEFAULT_FACE_VECTOR_DIM = 512;
    private static final Long DEFAULT_CREATE_COLLECTION_TIME_OUT = 2000L;

    private static final String INDEX_NAME = "faceIndex";

    private static final IndexType INDEX_TYPE = IndexType.IVF_FLAT;
    private static final String INDEX_PARAM = "{\"nlist\":128}";

//    private static final Integer SEARCH_K = 5;
    private static final String SEARCH_PARAM = "{\"nprobe\":10}";

    public MilvusClientService(MilvusServiceClient milvusClient) {
        this.milvusClient = milvusClient;
    }


    private void handleResponseStatus(R<?> r) {
        if (r.getStatus() != R.Status.Success.getCode()) {
            throw new MilvusException("status: " + r.getStatus() + "; message: " + r.getMessage());
        }
    }

    public void createFaceCollection(String name) {
        createFaceCollection(name, DEFAULT_FACE_VECTOR_DIM, DEFAULT_CREATE_COLLECTION_TIME_OUT);
    }

    public void createFaceCollection(String name, int dim, long timeoutMilliseconds) {
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


        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(name)
                .withDescription("face repo")
                .withShardsNum(2)
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .build();
        R<RpcStatus> response = milvusClient.withTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
                .createCollection(createCollectionReq);
        handleResponseStatus(response);
    }

    public void dropCollection(String name) {
        R<RpcStatus> response = milvusClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
    }

    public boolean hasCollection(String name) {
        R<Boolean> response = milvusClient.hasCollection(HasCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    public R<RpcStatus> loadCollection(String name) {
        R<RpcStatus> response = milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        return response;
    }

    public R<RpcStatus> releaseCollection(String name) {
        R<RpcStatus> response = milvusClient.releaseCollection(ReleaseCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        return response;
    }

    public R<DescribeCollectionResponse> describeCollection(String name) {
        R<DescribeCollectionResponse> response = milvusClient.describeCollection(DescribeCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        DescCollResponseWrapper wrapper = new DescCollResponseWrapper(response.getData());
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
        return response;
    }

    public R<RpcStatus> createPartition(String name, String partitionName) {
        R<RpcStatus> response = milvusClient.createPartition(CreatePartitionParam.newBuilder()
                .withCollectionName(name)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        return response;
    }

    private R<RpcStatus> dropPartition(String name, String partitionName) {
        System.out.println("========== dropPartition() ==========");
        R<RpcStatus> response = milvusClient.dropPartition(DropPartitionParam.newBuilder()
                .withCollectionName(name)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private R<Boolean> hasPartition(String name, String partitionName) {
        System.out.println("========== hasPartition() ==========");
        R<Boolean> response = milvusClient.hasPartition(HasPartitionParam.newBuilder()
                .withCollectionName(name)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private R<RpcStatus> releasePartition(String name, String partitionName) {
        R<RpcStatus> response = milvusClient.releasePartitions(ReleasePartitionsParam.newBuilder()
                .withCollectionName(name)
                .addPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<ShowCollectionsResponse> showCollections() {
        R<ShowCollectionsResponse> response = milvusClient.showCollections(ShowCollectionsParam.newBuilder()
                .build());
        handleResponseStatus(response);
        return response;
    }

    private R<ShowPartitionsResponse> showPartitions(String name) {
        R<ShowPartitionsResponse> response = milvusClient.showPartitions(ShowPartitionsParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        return response;
    }

    public R<RpcStatus> createIndex(String name) {
        R<RpcStatus> response = milvusClient.createIndex(CreateIndexParam.newBuilder()
                .withCollectionName(name)
                .withFieldName(VECTOR_FIELD)
                .withIndexName(INDEX_NAME)
                .withIndexType(INDEX_TYPE)
                .withMetricType(MetricType.IP)
                .withExtraParam(INDEX_PARAM)
                .withSyncMode(Boolean.TRUE)
                .build());
        handleResponseStatus(response);
        return response;
    }

    private R<RpcStatus> dropIndex(String name) {
        R<RpcStatus> response = milvusClient.dropIndex(DropIndexParam.newBuilder()
                .withCollectionName(name)
                .withIndexName(INDEX_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    private R<DescribeIndexResponse> describeIndex(String name) {
        R<DescribeIndexResponse> response = milvusClient.describeIndex(DescribeIndexParam.newBuilder()
                .withCollectionName(name)
                .withIndexName(INDEX_NAME)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private R<GetIndexStateResponse> getIndexState(String name) {
        R<GetIndexStateResponse> response = milvusClient.getIndexState(GetIndexStateParam.newBuilder()
                .withCollectionName(name)
                .withIndexName(INDEX_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    private R<GetIndexBuildProgressResponse> getIndexBuildProgress(String name) {
        R<GetIndexBuildProgressResponse> response = milvusClient.getIndexBuildProgress(
                GetIndexBuildProgressParam.newBuilder()
                        .withCollectionName(name)
                        .withIndexName(INDEX_NAME)
                        .build());
        handleResponseStatus(response);
        return response;
    }

    private R<MutationResult> delete(String name, String partitionName, String expr) {
        System.out.println("========== delete() ==========");
        DeleteParam build = DeleteParam.newBuilder()
                .withCollectionName(name)
                .withPartitionName(partitionName)
                .withExpr(expr)
                .build();
        R<MutationResult> response = milvusClient.delete(build);
        handleResponseStatus(response);
        System.out.println(response.getData());
        return response;
    }

    private R<MutationResult> insert(String name, String partitionName, List<List<Float>> features) {
        List<InsertParam.Field> fields = new ArrayList<>();
//        fields.add(new InsertParam.Field(AGE_FIELD, ages));
        fields.add(new InsertParam.Field(VECTOR_FIELD, features));
//        fields.add(new InsertParam.Field(PROFILE_FIELD, profiles));

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(name)
                .withPartitionName(partitionName)
                .withFields(fields)
                .build();

        R<MutationResult> response = milvusClient.insert(insertParam);
        handleResponseStatus(response);
        MutationResultWrapper wrapper = new MutationResultWrapper(response.getData());
        return response;
    }

    private R<SearchResults> searchFace(String name, List<List<Float>> features, int topk, String expr, int nprobe) {
        System.out.println("========== searchFace() ==========");
        long begin = System.currentTimeMillis();

//        List<String> outFields = Collections.singletonList(AGE_FIELD);
//        List<List<Float>> vectors = generateFloatVectors(5);

        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(name)
                .withMetricType(MetricType.IP)
//                .withOutFields(outFields)
                .withTopK(topk)
                .withVectors(features)
                .withVectorFieldName(VECTOR_FIELD)
                .withExpr(expr)
                .withParams("{\"nprobe\":" + nprobe + "}")
//                .withGuaranteeTimestamp(Constant.GUARANTEE_EVENTUALLY_TS)
                .build();

        R<SearchResults> response = milvusClient.search(searchParam);
        long end = System.currentTimeMillis();
        long cost = (end - begin);
        System.out.println("Search time cost: " + cost + "ms");

        handleResponseStatus(response);
        SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());
        for (int i = 0; i < features.size(); ++i) {
            System.out.println("Search result of No." + i);
            List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(i);
            System.out.println(scores);
            System.out.println("Output field data for No." + i);
//            System.out.println(wrapper.getFieldData(AGE_FIELD, i));
        }

        return response;
    }

    private R<QueryResults> query(String name, String expr) {
        System.out.println("========== query() ==========");
//        List<String> fields = Arrays.asList(ID_FIELD, AGE_FIELD);
        QueryParam test = QueryParam.newBuilder()
                .withCollectionName(name)
                .withExpr(expr)
//                .withOutFields(fields)
                .build();
        R<QueryResults> response = milvusClient.query(test);
        handleResponseStatus(response);
        QueryResultsWrapper wrapper = new QueryResultsWrapper(response.getData());
        System.out.println(ID_FIELD + ":" + wrapper.getFieldWrapper(ID_FIELD).getFieldData().toString());
//        System.out.println(AGE_FIELD + ":" + wrapper.getFieldWrapper(AGE_FIELD).getFieldData().toString());
        System.out.println("Query row count: " + wrapper.getFieldWrapper(ID_FIELD).getRowCount());
        return response;
    }

    private R<ManualCompactionResponse> compact(String name) {
        System.out.println("========== compact() ==========");
        R<ManualCompactionResponse> response = milvusClient.manualCompact(ManualCompactParam.newBuilder()
                .withCollectionName(name)
                .build());
        handleResponseStatus(response);
        return response;
    }
}
