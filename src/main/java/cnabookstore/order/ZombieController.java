package cnabookstore.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ZombieController {

    private boolean flag;

    public ZombieController(){
        flag = true;
    }

    @GetMapping({"/isHealthy"})
    public void zombie2() throws Exception {
        if (flag)
            return;
        else
            throw new Exception("zombie.....");
    }

    @GetMapping({"/makeZombie"})
    public void getStockInputs() {

        flag = false;

    }

}
