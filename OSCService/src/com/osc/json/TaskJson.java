package com.osc.json;

/**
 * 
 * @author siva kurapati
 *
 */
public class TaskJson extends BaseJson {
	private String taskName;
	private Integer assignedToId;
	private String assignedTo;
	private String status;
	private String description;
	private String dueDate;
	private String doubt;
	private Boolean isDoubt;

	public Integer getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(Integer assignedToId) {
		this.assignedToId = assignedToId;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDoubt() {
		return doubt;
	}

	public void setDoubt(String doubt) {
		this.doubt = doubt;
	}

	public Boolean getIsDoubt() {
		return isDoubt;
	}

	public void setIsDoubt(Boolean isDoubt) {
		this.isDoubt = isDoubt;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
