package softuniBlog.bindingModel;

import javax.validation.constraints.NotNull;

/**
 * Created by 1234 on 19.12.2016 г..
 */
public class CommentBindingModel {
    @NotNull
    private String content;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
