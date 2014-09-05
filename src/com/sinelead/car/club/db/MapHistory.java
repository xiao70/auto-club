package com.sinelead.car.club.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "map_history")
public class MapHistory
{
	@DatabaseField(id = true)
	// sqlite integer类型的可以自增长
	private Integer id;
	@DatabaseField(unique = true)
	private String title;
	@DatabaseField
	private String details;
	@DatabaseField
	private String adcode;

	public String getAdcode()
	{
		return adcode;
	}

	public void setAdcode(String adcode)
	{
		this.adcode = adcode;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDetails()
	{
		return details;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

}
