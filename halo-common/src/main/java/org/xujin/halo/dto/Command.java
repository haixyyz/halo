package org.xujin.halo.dto;

/**
 * Command stands for a request from Client.
 * According CommandExecutor will help to handle the business logic. This is a classic Command Pattern
 *
 * @author xujin
 */
public abstract class Command extends DTO{

    private static final long serialVersionUID = 1L;

	/**
	 * 不需要操作人设置此值为true
	 */
	private boolean operaterIsNotNeeded = false;

    /**
     * command的操作人
     */
	private String operater;

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public boolean isOperaterIsNotNeeded() {
		return operaterIsNotNeeded;
	}

	public void setOperaterIsNotNeeded(boolean operaterIsNotNeeded) {
		this.operaterIsNotNeeded = operaterIsNotNeeded;
	}
}
