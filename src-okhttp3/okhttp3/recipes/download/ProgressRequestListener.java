package okhttp3.recipes.download;

public interface ProgressRequestListener {
	void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
