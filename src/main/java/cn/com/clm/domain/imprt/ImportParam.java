package cn.com.clm.domain.imprt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * describe: import param
 *
 * @author liming.cao
 */
@Component
public class ImportParam {

    @Autowired
    private PeopleItem peopleItem;

    public PeopleItem getPeopleItem() {
        return peopleItem;
    }

    public void setPeopleItem(PeopleItem peopleItem) {
        this.peopleItem = peopleItem;
    }
}
