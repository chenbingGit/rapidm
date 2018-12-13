package com.chenbing.rapidm.util.menu;

/**
 * 
 * @author hcl
 * @date 2013-09-03
 */
public class ComplexButton extends Button
{
	private Button[] sub_button;
	
	public Button[] getSub_button()
	{
		return sub_button;
	}
	
	public void setSub_button(Button[] sub_button)
	{
		this.sub_button = sub_button;
	}
}