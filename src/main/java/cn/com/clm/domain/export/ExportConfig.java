package cn.com.clm.domain.export;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * describe: export config
 *
 * @author liming.cao
 */
@Component
public class ExportConfig {
    @Value("#{'${export.headers.people:\"\"}'.split(',')}")
    private List<String> peopleHeaders;
    @Value("#{'${export.fields.people:\"\"}'.split(',')}")
    private List<String> peopleFields;
    @Value("#{'${export.titles.people:\"\"}'.split(',')}")
    private List<String> peopleTitles;

    public List<String> getPeopleHeaders() {
        return peopleHeaders;
    }

    public void setPeopleHeaders(List<String> peopleHeaders) {
        this.peopleHeaders = peopleHeaders;
    }

    public List<String> getPeopleFields() {
        return peopleFields;
    }

    public void setPeopleFields(List<String> peopleFields) {
        this.peopleFields = peopleFields;
    }

    public List<String> getPeopleTitles() {
        return peopleTitles;
    }

    public void setPeopleTitles(List<String> peopleTitles) {
        this.peopleTitles = peopleTitles;
    }
}
