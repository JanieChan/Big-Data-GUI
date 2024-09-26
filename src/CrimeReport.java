import java.sql.Time;
import java.time.*;
import java.util.*;

public class CrimeReport implements Comparable<CrimeReport> {
	public static enum Category{
		ROBBERY, VEHICLE_THEFT, ASSAULT, TRESPASS, BURGLARY, LARCENY_THEFT, WARRANTS, OTHER_OFFENSES, FRAUD, 
		MISSING_PERSON, DRUG_NARCOTIC, SUSPICIOUS_OCC, NON_CRIMINAL, WEAPON_LAWS, LIQUOR_LAWS, VANDALISM,
		SEX_OFFENSES_FORCIBLE, LOITERING, FORGERY_COUNTERFEITING, SECONDARY_CODES, DRUNKENNESS, KIDNAPPING, STOLEN_PROPERTY,
		RECOVERED_VEHICLE, PROSTITUTION, ARSON, EMBEZZLEMENT, DISORDERLY_CONDUCT, DRIVING_UNDER_THE_INFLUENCE; 
		public static Category getCategory(String categoryString) {
			for(Category category : Category.values()) {
				if(category.toString().equalsIgnoreCase(categoryString)) {
					return category;
				}
			}
			throw new IllegalArgumentException(categoryString + " is not a valid String for Category");
		}
		
		@Override
		public String toString() {
			String text = super.toString().replaceAll("_", " ");
			return text.substring(0,1) + text.substring(1).toLowerCase();
		}
	}
	
	public static enum Resolution{
		NONE, ARREST_BOOKED, ARREST_CITED, PSYCHOPATHIC_CASE, NOT_PROSECUTED, LOCATED, UNFOUNDED, DISTRICT_ATTORNEY_REFUSES_TO_PROSECUTE,
		COMPLAINANT_REFUSES_TO_PROSECUTE, EXCEPTIONAL_CLEARANCE, PROSECUTED_BY_OUTSIDE_AGENCY; 
		public static Resolution getResolution(String resolutionString) {
			for(Resolution resolution : Resolution.values()) {
				if(resolution.toString().equalsIgnoreCase(resolutionString)) {
					return resolution;
				}
			}
			throw new IllegalArgumentException(resolutionString + " is not a valid String for Resolution");
		}
		
		@Override
		public String toString() {
			String text = super.toString().replaceAll("_", " ");
			return text.substring(0,1) + text.substring(1).toLowerCase();
		}
	}
	
	private int incidentNum;
	private Category category;
	private String description;
	private String dayOfWeek;
	private String date;
	private String time;
	private Resolution resolution;
	private String address;
	
	public CrimeReport(int incidentNum, Category category, String description, String dayOfWeek,
		   String date, String time, Resolution resolution, String address) {
		this.incidentNum = incidentNum;
		this.category = category;
		this.description = description;
		this.dayOfWeek = dayOfWeek;
		this.date = date;
		this.time = time;
		this.resolution = resolution;
		this.address = address;
	}
	
	public int getIncidentNum() {
		return incidentNum;
	}
	
	public void setIncidentNum(int incidentNum) {
		this.incidentNum = incidentNum;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public Resolution getResolution() {
		return resolution;
	}
	
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Incident Number: " + incidentNum + "\nCategory: " + category +
				"\nDescription: " + description + "\nDate & Time: " + dayOfWeek + 
				" " + date + " " + time + "\nResolution: " + resolution + "\nAddress: " + address;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CrimeReport) {
			CrimeReport other = (CrimeReport) obj;
			return this.incidentNum == other.incidentNum &&
					this.category.equals(other.category) &&
					this.description.equals(other.description) &&
					this.dayOfWeek.equals(other.dayOfWeek) &&
					this.date.equals(other.date) &&
					this.time.equals(other.time) &&
					this.resolution.equals(other.resolution) &&
					this.address.equals(other.address);
		} else {
			return false;
		}
	}
	@Override
	public int compareTo(CrimeReport other) {
		int dateCompare = this.date.compareTo(other.date);
		
		if(dateCompare != 0) {
			return dateCompare;
		}else {
			return this.time.compareTo(other.time);
		}
	}
}
