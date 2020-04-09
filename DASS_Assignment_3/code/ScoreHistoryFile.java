/*

  To change this generated comment edit the template variable "typecomment":
  Window>Preferences>Java>Templates.
  To enable and disable the creation of type comments go to
  Window>Preferences>Java>Code Generation.
 */

import java.util.*;
import java.io.*;

public class ScoreHistoryFile {

	private static String SCOREHISTORY_DAT = "DASS_Assignment_3/SCOREHISTORY.DAT";

	public static void addScore(String nick, String date, String score)
		throws IOException {

		String data = nick + "\t" + date + "\t" + score + "\n";

		RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw");
		out.skipBytes((int) out.length());
		out.writeBytes(data);
		out.close();
	}

	public static Vector getScores(String nick)
		throws IOException {
		Vector scores = new Vector();
		if(nick == "???")
		{
			return gettopScores();
		}

		BufferedReader in =new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String data;
		while ((data = in.readLine()) != null) {
			// File format is nick\tfname\te-mail
			String[] scoredata = data.split("\t");
			//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
			if (nick.equals(scoredata[0])) {
				scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
			}
		}
		return scores;	
	}


	public static Vector gettopScores()
	throws IOException {
	Vector scores = new Vector();

	BufferedReader in =new BufferedReader(new FileReader(SCOREHISTORY_DAT));
	String data;
	while ((data = in.readLine()) != null) {
		// File format is nick\tfname\te-mail
		String[] scoredata = data.split("\t");
		//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
		scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
		// ScoreHistoryFile sh = new ScoreHistoryFile();
		// Comparator<Score> compare = sh.new comparision();
		// Collections.sort(scores, compare);
	}	
	return scores;	
}

class comparision implements Comparator<Score>{

	@Override
	public int compare(Score sc1, Score sc2) {
		// System.out.println(Integer.parseInt(sc2.getScore()) - Integer.parseInt(sc1.getScore()));
		// return sc1.getScore().compareTo(sc2.getScore());
		return Integer.parseInt(sc2.getScore()) - Integer.parseInt(sc1.getScore()); 
	}

}

}
