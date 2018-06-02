package sdpsose2018.hrms;

public class PerformanceReview {
	
	int employeeId;
	int reviewerId;
	String comment;
	int rating;
	
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

	@Override
	public String toString() {
		return "PerformanceReview [employeeId=" + employeeId + ", reviewerId=" + reviewerId + ", comment=" + comment
				+ ", rating=" + rating + "]";
	}

	public PerformanceReview() { }

}
