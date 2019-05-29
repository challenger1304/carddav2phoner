package de.darlor.dacardconv.utils;

import de.darlor.dacardconv.exceptions.WebDAVException;
import de.darlor.dacardconv.exceptions.WebDAVMalformedURLException;
import de.darlor.dacardconv.exceptions.WebDAVResponseBlockedException;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Base64;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class HttpsWebDAVRequest {

	private final URL SERVER;
	private final String AUTH;

	public HttpsWebDAVRequest(URL serverBaseURL, String username, String password) {
		this.SERVER = serverBaseURL;
		this.AUTH = Base64.getEncoder().encodeToString(
				(username + ":" + password).getBytes()); //base64 of username:password;

	}

	public void allowSelfSignedCerts() {
		//TODO implement
	}

	private void sendRequest() throws WebDAVException {
		try {
			HttpsURLConnection con = (HttpsURLConnection) SERVER.openConnection();
			con.setRequestMethod("GET");
			Integer status = con.getResponseCode();
			if (200 != status) {
				throw new WebDAVResponseBlockedException("Connection refused", status);
			}
		} catch (SSLException e) {
			throw new WebDAVMalformedURLException("SSL-Certificate of the server isn't trusted", e);
		} catch (IOException e) {
			throw new WebDAVMalformedURLException("Server isn't reachable", e);
		}
	}


	private static class TrustAllX509TrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] xcs, String string) {
			// nothing
		}

		@Override
		public void checkServerTrusted(X509Certificate[] xcs, String string) {
			// nothing
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

	}

	private static class VerifyAllHostnameVerififer implements HostnameVerifier {

		@Override
		public boolean verify(String string, SSLSession ssls) {
			return true;
		}

	}
}
