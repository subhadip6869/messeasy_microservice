package app.netlify.dsubha.repository;

import org.springframework.http.HttpStatus;

public class ResponseRepository<T> {
	private int status;
	private String message;
	private T data;

	public ResponseRepository(HttpStatus status, String message, T data) {
		this.status = status.value();
		this.message = message;
		this.data = data;
	}

	public ResponseRepository(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseRepository [status=" + status + ", message=" + message + ", data=" + data + "]";
	}

}
