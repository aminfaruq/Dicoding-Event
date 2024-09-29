package com.aminfaruq.dicodingevent.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class EventResponse(

	@field:SerializedName("listEvents")
	val listEvents: List<EventDetail>?,

	@field:SerializedName("error")
	val error: Boolean?,

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("event")
	val event: EventDetail
)

@Entity(tableName = "event")
data class EventDetail(
	@PrimaryKey
	@ColumnInfo(name = "id")
	@SerializedName("id")
	val id: Int?,

	@ColumnInfo(name = "summary")
	@SerializedName("summary")
	val summary: String?,

	@ColumnInfo(name = "mediaCover")
	@SerializedName("mediaCover")
	val mediaCover: String?,

	@ColumnInfo(name = "registrants")
	@SerializedName("registrants")
	val registrants: Int?,

	@ColumnInfo(name = "imageLogo")
	@SerializedName("imageLogo")
	val imageLogo: String?,

	@ColumnInfo(name = "link")
	@SerializedName("link")
	val link: String?,

	@ColumnInfo(name = "description")
	@SerializedName("description")
	val description: String?,

	@ColumnInfo(name = "ownerName")
	@SerializedName("ownerName")
	val ownerName: String?,

	@ColumnInfo(name = "cityName")
	@SerializedName("cityName")
	val cityName: String?,

	@ColumnInfo(name = "quota")
	@SerializedName("quota")
	val quota: Int?,

	@ColumnInfo(name = "name")
	@SerializedName("name")
	val name: String?,

	@ColumnInfo(name = "beginTime")
	@SerializedName("beginTime")
	val beginTime: String?,

	@ColumnInfo(name = "endTime")
	@SerializedName("endTime")
	val endTime: String?,

	@ColumnInfo(name = "category")
	@SerializedName("category")
	val category: String?
)