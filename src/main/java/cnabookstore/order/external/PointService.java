
package cnabookstore.order.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="point", url="${api.url.point}")
public interface PointService {

    @RequestMapping(method= RequestMethod.GET, path="/points/{pointId}")
    public Point queryPoint(@PathVariable("pointId") Long pointId);

}