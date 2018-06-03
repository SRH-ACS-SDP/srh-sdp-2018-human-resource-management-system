package sdpsose2018.hrms;

import javax.persistence.*;

@Table(name = "performance_reviews")
public class PerformanceReview {
	
	@JoinColumn(name = "employee_id")
	@Column(name = "employee_id")
	int employeeId;
	
	@JoinColumn(name = "employee_id")
	@Column(name = "employee_id")
	int reviewerId;
	
	String comment;
	
	int rating;
	
	int date; 
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(int reviewerId) {
		this.reviewerId = reviewerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "PerformanceReview [employeeId=" + employeeId + ", reviewerId=" + reviewerId + ", comment=" + comment
				+ ", rating=" + rating + ", date=" + date + "]";
	}

	public PerformanceReview() { }

}
