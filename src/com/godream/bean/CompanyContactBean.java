package com.godream.bean;

import android.graphics.drawable.Drawable;

/**
 * 类名：CompanyContactBean
 * 
 * 描述：公司联系人
 * 
 * 
 * @author lipeng
 *
 */
public class CompanyContactBean{
	private String contactId;//联系人编号
	private String groupId;//组织编号
	private String name;//姓名
	private String birthday;//生日
	private String nameIndex;//联系人索引(姓名首字母)
	private String nameLetter;//名字的汉语拼音
	private String gender;//性别
	private String address;//联系地址
	private String telephone;//办公电话
	private String email;//个人邮箱
	private String photo;//头像路径
	private String updatetime;//最近更新时间
	private String createtime;//创建时间
	private Drawable drawable;
	private String status;//在线状态
	private String formattedNumber;// 汉语名字对应的12键盘上的数字，如白洁：对应的是25
	private String groupName;//部门名称
	private String qq;
	private String weixin;
	private String skype;
	private String msn;
	private String sinaweibo;
	
	/*zhangjg 添加   实现将联系人信息转换成名片*/
	public String getContactAsCard(){
		StringBuilder sb = new StringBuilder("");
		
		sb.append("姓名： ").append(name).append("\n");
		if(telephone != null && !"".equals(telephone)){
			sb.append("电话： ").append(telephone).append("\n");
		}
		
		if(email != null && !"".equals(email)){
			sb.append("邮箱： ").append(email).append("\n");
		}
		
		if(address != null && !"".equals(address)){
			sb.append("地址： ").append(address).append("\n");
		}
		
		
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
		
	}
	
//	private Bitmap icon;
//	
//	public Bitmap getIcon() {
//		return icon;
//	}
//	public void setIcon(Bitmap icon) {
//		this.icon = icon;
//	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getSinaweibo() {
		return sinaweibo;
	}
	public void setSinaweibo(String sinaweibo) {
		this.sinaweibo = sinaweibo;
	}
	public String getFormattedNumber() {
		return formattedNumber;
	}
	public void setFormattedNumber(String formattedNumber) {
		this.formattedNumber = formattedNumber;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getNameIndex() {
		return nameIndex;
	}
	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}
	public String getNameLetter() {
		return nameLetter;
	}
	public void setNameLetter(String nameLetter) {
		this.nameLetter = nameLetter;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String workPlace) {
		this.gender = workPlace;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	

	
}
