package top.itcat;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalTest {
//    @Autowired
//    private HosipitalRepository hosipitalRepository;
//    @Resource
//    private JmsMessagingTemplate jmsMessagingTemplate;
//    @Resource(name = "orderDelayCheckDestination")
//    private Destination orderDelayCheckDestination;
//
//    @Test
//    public void testSendDelay() throws InterruptedException {
//        System.out.println((int) TimeUnit.MINUTES.toMillis(30));
//        AppointmentMsg appointmentMsg = AppointmentMsg.builder().orderId("asdasd").build();
//
//        SendMsgUtil.sendMessageDelay(jmsMessagingTemplate,
//                orderDelayCheckDestination, appointmentMsg, 1000);
//        SendMsgUtil.sendMessageDelay(jmsMessagingTemplate,
//                orderDelayCheckDestination, appointmentMsg, 1000);
//        SendMsgUtil.sendMessageDelay(jmsMessagingTemplate,
//                orderDelayCheckDestination, appointmentMsg, 1000);
//        System.out.println("send");
//        Thread.sleep(2000);
//    }
//
//    @Test
//    public void testSava() {
//        Hosipital h = new Hosipital();
//        h.setEmail("asd");
//        h.setPhonenum("111");
//        h.setId(1);
//        h.setName("new");
//
//        for (int i = 1; i < 10; ++i) {
//            h.setId(i);
//            if ((i & 1) == 0)
//                h.setName("好医院 " + i);
//            else
//                h.setName("普通医院 " + i);
//
//            hosipitalRepository.save(h);
//        }
//    }
//
//    @Test
//    public void testDel() {
//        hosipitalRepository.deleteAll();
//    }
//
//    @Test
//    public void testQuery() {
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
//                .add(QueryBuilders.matchQuery("name", "好"),
//                        ScoreFunctionBuilders.weightFactorFunction(100))
//                .add(QueryBuilders.matchQuery("name", "医院"),
//                        ScoreFunctionBuilders.weightFactorFunction(50))
//                .scoreMode("sum")
//                .setMinScore(10);
//
////        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        Pageable pageable = new PageRequest(0, 12);
//        queryBuilder.withPageable(pageable)
//                .withQuery(functionScoreQueryBuilder).build();
//        Page<Hosipital> page = hosipitalRepository.search(queryBuilder.build());
//
//        for (Hosipital h : page.getContent()) {
//            System.out.println(h);
//        }
//    }
//
//    @Test
//    public void testk() {
//        JestClientFactory factory = new JestClientFactory();
//        factory.setHttpClientConfig(new HttpClientConfig.Builder("http://127.0.0.1:9200").connTimeout(60000).readTimeout(60000).multiThreaded(true).build());
//        JestClient jestClient = factory.getObject();
//
//        List<Object> objs = new ArrayList<Object>();
//        Good good = new Good();
//        good.setId(1);
//        good.setTitle("test");
//        Good good1 = new Good();
//        good.setId(2);
//        good.setTitle("test2");
//        Good good2 = new Good();
//        good.setId(3);
//        good.setTitle("test3");
//        objs.add(good);
//        objs.add(good1);
//        objs.add(good2);
//        boolean result = false;
//        try {
//            result = insertBatch(jestClient,"goods", "good",objs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("批量新增:"+result);
//    }
//
//    public static boolean insertBatch(JestClient jestClient,String indexName, String typeName, List<Object> objs) throws Exception {
//        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
//        for (Object obj : objs) {
//            Index index = new Index.Builder(obj).build();
//            bulk.addAction(index);
//        }
//        BulkResult br = jestClient.execute(bulk.build());
//        return br.isSucceeded();
//    }
}
