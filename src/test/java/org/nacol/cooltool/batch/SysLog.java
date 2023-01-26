package org.nacol.cooltool.batch;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Accessors(chain = true)
@Component
public class SysLog {

    private Integer id;

    private String msg;

}
