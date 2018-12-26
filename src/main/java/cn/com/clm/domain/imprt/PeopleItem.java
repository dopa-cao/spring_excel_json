package cn.com.clm.domain.imprt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * describe: people item
 *
 * @author liming.cao
 */
@Component
public class PeopleItem implements IParam{
    @Value("${import.people.cellCount}")
    private Integer cellCount;
    @Value("#{'${import.people.fieldNames:\"\"}'.split(',')}")
    private List<String> fieldNames;
    @Value("#{'${import.people.dateFields:\"\"}'.split(',')}")
    private List<String> dateFields;

    @Override
    public Integer getCellCount() {
        return cellCount;
    }

    public void setCellCount(Integer cellCount) {
        this.cellCount = cellCount;
    }

    @Override
    public List<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    @Override
    public List<String> getDateFields() {
        return dateFields;
    }

    public void setDateFields(List<String> dateFields) {
        this.dateFields = dateFields;
    }

    @Override
    public List<String> getTimeFields() {
        return Collections.emptyList();
    }
}
