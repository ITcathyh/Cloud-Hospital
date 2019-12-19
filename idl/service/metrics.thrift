/*
kitool new -s -i metrics.thrift -pre code.byted.org/neu/metrics -cmd thrift -trans Framed -proto Binary neu.hosipital.metrics
 */

namespace java top.itcat.rpc.service.metrics
namespace go neu_hosipital_metrics

include "base.thrift"

enum ServiceStatusEnum {
    Open = 0,
    Normal = 1,
    Exception = 2,
}

struct MetricsCountRequest {
    2: required string interfaceName,
    3: required ServiceStatusEnum status,
    4: required i64 time,
    255: required base.Base Base,
}

struct MetricsCountResponse {
    255: required base.BaseResp BaseResp,
}

service MetricsService {
    MetricsCountResponse MetricsCount(1:MetricsCountRequest req)
}