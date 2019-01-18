package weixin.popular.bean.massmessage;

public class Filter {

	private String tag_id;

	private boolean is_to_all; //true:群发， false:分组群发（group_id不为空）

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public boolean getIs_to_all() {
		return is_to_all;
	}

	public void setIs_to_all(boolean is_to_all) {
		this.is_to_all = is_to_all;
	}
}
