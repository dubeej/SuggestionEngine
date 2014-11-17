package Movies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Scanner {
	private static Movies movies;		// Movie database
	FileWriter writer2;					// File writer to write movie database
	File file2;							// File to writer the movie database to
	static String cookie;				// IMDB cookie

		

	public static String search(String movie) {
		String res = "GET /M/title-exact?" + movie + " HTTP/1.1\r\n" +
				"Host: www.imdb.com\r\n" +
				"User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0\r\n" +
				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
				"Accept-Language: en-US,en;q=0.5\r\n" +
				"Accept-Encoding: none\r\n" +
				"Connection: keep-alive\r\n\r\n";

		return res;
	}

	
	public static String getMovieData(String movie) {
		String res = "GET /title/" + movie + " HTTP/1.1\r\n" +
				"Host: www.imdb.com\r\n" +
				"User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0\r\n" +
				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
				"Accept-Language: en-US,en;q=0.5\r\n" +
				"Accept-Encoding: none\r\n" +
				"Cookie: " + cookie + "\r\n" + 
				"Connection: keep-alive\r\n\r\n";
	
		return res;
	}
	
	public static String parseSearch(String host, String packet) throws UnknownHostException, IOException {
    	PrintStream output;		// Client output
    	String buffer = "";
		Socket s = new Socket(host, 80);
	    output = new PrintStream(s.getOutputStream());
	    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		output.print(packet);;
		output.flush();
		
		String line;
		String url = null;
		
		while ((line = input.readLine()) != null) {
			if (line.length() == 0) 
					continue;
			
			
			buffer = buffer + line;
	
			if (buffer.indexOf("Content-Length: 0") > 0) {
				
				if (buffer.indexOf("Set-Cookie: ") > -1) {
					String [] cookies;
					cookie = "";
					cookies = buffer.split("Set-Cookie: ");
					for (int i = 1; i < cookies.length; i++) {
						cookie = cookie + splitStr(cookies[i], "", ";") + "; ";
					}
				}
				
				int pos;
				
				pos = buffer.indexOf("Location: ");
				
				if (pos > -1) {
					buffer = buffer.substring(pos + 10);
			
					pos = buffer.indexOf("Content-Type:");
				
					if (pos > -1) {
						url = buffer.substring(0, pos);
						
					}
				}
				
				s.close();
				break;
			}
		} 
		
		return url;
	}
	
	public static String splitStr(String data, String del1, String del2) {
		int pos1, pos2;
		String res = null;
		
		pos1 = data.indexOf(del1);
		if (pos1 > -1) {

			pos2 = data.indexOf(del2, pos1);
			if (pos2 > -1) {
				
				res = data.substring(pos1 + del1.length(), pos2);

			}
		}
		
		return res;
	}
	public static String parseMovieData(String host, String packet, int index) throws UnknownHostException, IOException {
    	PrintStream output;		// Client output
    	String buffer = "";
		Socket s = new Socket(host, 80);
	    output = new PrintStream(s.getOutputStream());
	    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		output.print(packet);;
		output.flush();
		
		String line;
		
		while ((line = input.readLine()) != null) {
			if (line.length() == 0) 
					continue;
			
			buffer = buffer + line;
	
			if (buffer.indexOf("<div id=\"servertime\"") >-1) {
				//System.out.println(buffer);
				String length = null;
				String contentRating = null;
				String director = null;
				String temp = null;

				length = splitStr(buffer, "<time itemprop=\"duration\" datetime=\"PT", "M");
				System.out.println("length: " + length);
				
				movies.getMovie(index).setLength(length);

				contentRating = splitStr(buffer, "itemprop=\"contentRating\" content=\"", "\"></span>");
				System.out.println("contentRating: " + contentRating);
						
				
				movies.getMovie(index).setCensorRating(contentRating);
	
				
				String lang;
				lang = splitStr(buffer, "<a href=\"/language/", "?");
				System.out.println("language: " + lang);
				
				movies.getMovie(index).setLanguage(lang);
				

				
				String country;
				country = splitStr(buffer, "<a href=\"/country/", "?");
				System.out.println("country: " + country);
				movies.getMovie(index).setCountry(country);
				
				String [] tokens;
				
				temp = splitStr(buffer, "itemprop=\"director\"", "</div>");
				
				if (temp != null) {
				
					tokens = temp.split("<span class");
					if (tokens.length > 0) {
						String directors [];
						directors = new String [2];
						for (int i = 1; i < tokens.length; i++) {
								directors[i - 1] = splitStr(tokens[i], ">", "<");
								System.out.println("director: " + directors[i - 1] );
								if (i >= 2) 
									
									break;
								
						}
						movies.getMovie(index).setDirectors(directors);
					}
				} else 
					System.out.println(buffer);
				
				temp = splitStr(buffer, "itemprop=\"creator\"", "</div>");
				if (temp != null) {
					tokens = temp.split("<span class");
					if (tokens.length > 0) {
						String writers [];
						writers = new String [2];
						for (int i = 1; i < tokens.length; i++) {
							writers[i - 1] = splitStr(tokens[i], ">", "<");
							System.out.println("writers: " + writers[i - 1] );
							if (i >= 2) {
								break;
							}
						}
						movies.getMovie(index).setWriters(writers);
						
					}
				}else 
					System.out.println(buffer);
				
				temp = splitStr(buffer, "itemprop=\"actors\"", "</div>");

				if (temp != null) {
					tokens = temp.split("<span class=\"itemprop\"");
					if (tokens.length > 0) {
						String stars [];
						stars = new String [3];
						for (int i = 1; i < tokens.length; i++) {
							stars[i - 1] = splitStr(tokens[i], ">", "<");
							System.out.println("stars: " + stars[i - 1] );
							if (i >= 3) {
								break;
							}
							
								
						}
						movies.getMovie(index).setStars(stars);
						
					}
				}else 
					System.out.println(buffer);
		
				s.close();
				break;
			}
		} 
		
		return buffer;
	}
	
	public String parseSearch(String buffer) {
		int pos;
		String url = null;
		
		pos = buffer.indexOf("Location: ");
		
		if (pos > -1) {
			buffer = buffer.substring(pos + 10);

			pos = buffer.indexOf("Content-Type:");

			if (pos > -1) {
				url = buffer.substring(0, pos);
				
			}
		}
		
		return url;
	}
	
	public void scan() throws IOException {
		String url;
		
		String buffer;
		ArrayList<String> notFound;
		
		notFound = new ArrayList<String>();
		movies = new Movies();
		

	      file2 = new File("Test2.txt");

	      file2.createNewFile();

	      writer2 = new FileWriter(file2); 
	  
	 
		
		try {
			movies.load("C:\\Users\\Dubee\\Desktop\\ml-100k\\ml-100k\\u.item");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < movies.size(); i++) {
			url = movies.getMovie(i).getIMDB();
			if (url != null && url.length() > 33) {
				url = url.substring(33);

				int pos;
				pos = url.indexOf(",%20The%20(");

				if (pos > -1) 
					url = "The%20" + url.replace(",%20The%20(", "%20(");

				String newURL;
				System.out.println("finding " + url);
				try {
					newURL = parseSearch("www.imdb.com", search(url));
					
					pos = newURL.indexOf("http://www.imdb.com/title/");
					if (pos > -1) {
						newURL = newURL.substring(26);
						buffer = parseMovieData("www.imdb.com", getMovieData(newURL), i);
						System.out.println("found: " + url);
					} else {
						notFound.add(url);
						System.out.println("not found: " + url);
					}
						
					System.out.println();
					
					writer2.write(movies.getMovie(i).toString() + "\n"); 
					writer2.flush();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

		}
		
		writer2.close();
		
		//for (int i = 0; i < notFound.size(); i++)
		//	System.out.println(notFound.get(i));
		
	}
}
