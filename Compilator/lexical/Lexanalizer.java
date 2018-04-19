package lexical;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.Error;
//import errors.ErrorLexico;
import java.io.*;


public class Lexanalizer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

  private Lexer_ops ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yychar+1;}
  private ComplexSymbolFactory factSimbolos;
  private int lineacs,columnacs;
  public Lexanalizer(Reader r, ComplexSymbolFactory sf){
	this(r);
    //symbolFactory = sf;
  }
  public Symbol symbol(String name, int code){
	return factSimbolos.newSymbol(name, code,new Location(yyline+1,yychar+1-yylength()),new Location(yyline+1,yychar+1));
  }
  public Symbol symbol(String name, int code, String lexem){
	return factSimbolos.newSymbol(name, code, new Location(yyline+1, yychar +1), new Location(yyline+1,yychar+yylength()), lexem);
  }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Lexanalizer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Lexanalizer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexanalizer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

  ops = new Lexer_ops(this);
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NOT_ACCEPT,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NOT_ACCEPT,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NOT_ACCEPT,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NOT_ACCEPT,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NOT_ACCEPT,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NOT_ACCEPT,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NOT_ACCEPT,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NOT_ACCEPT,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NOT_ACCEPT,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NOT_ACCEPT,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NOT_ACCEPT,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NOT_ACCEPT,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NOT_ACCEPT,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NOT_ACCEPT,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"12:8,13:2,14,12:2,13,12:18,13,12:7,39,40,12:2,3,15,50,20,16:10,46,12,19,17," +
"18,12:2,10,51,4,11,7,38,51:6,6,8,5,51:3,41,9,51,37,51:4,1,12,2,12,36,12,25," +
"30,34,43,27,45,52:2,35,52:2,31,24,23,22,28,52,29,26,32,42,33,52,44,21,52,48" +
",47,49,12:65410,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,147,
"0,1:4,2,1:2,3,4,5,6:2,1:3,6,1:8,6,7,1:2,6,1:2,6:2,1:2,6:5,1:2,6:2,1,8,1,6,9" +
",1,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33," +
"34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58," +
"59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83," +
"84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,6,101,102,103,104")[0];

	private int yy_nxt[][] = unpackFromString(105,53,
"1,2,3,4,5,50,54,50:4,57,6,7:2,51,8,55,9,10,6,11,12,59,61,132,142,63,143,142" +
",144,142:2,145,146,142,13,65,67,14,15,69,71,142,16,142,17,52,18,19,20,50,14" +
"2,-1:58,49,-1:63,8,-1:53,23,-1:50,24,-1:41,142:8,-1:4,142,-1:4,142:15,-1,14" +
"2:2,-1:2,142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:11,87,89,142:2,-1,14" +
"2:2,-1:2,142:5,-1:5,142:2,-1,46:13,-1,46:38,-1:6,68,-1:62,8,-1,21,-1:49,66," +
"-1:64,70,-1:60,53,-1:34,58,-1,22,60,-1:65,72,-1:11,74,-1:34,56,-1:42,30,-1:" +
"39,142:8,-1:4,142,-1:4,142,25,142:13,-1,142:2,-1:2,142:5,-1:5,142:2,-1:17,3" +
"1,-1:39,142:8,-1:4,142,-1:4,142:4,73,142,75,142:8,-1,142:2,-1:2,142:5,-1:5," +
"142:2,-1:25,34,-1:31,142:8,-1:4,142,-1:4,142:2,26,142:9,79,142:2,-1,142:2,-" +
"1:2,142:5,-1:5,142:2,-1:28,76,-1:61,27,-1:33,35,-1:72,28,-1:21,78,-1:72,62," +
"-1:14,64,-1:33,80,-1:33,142:8,-1:4,142,-1:4,142:2,29,142:12,-1,142:2,-1:2,1" +
"42:5,-1:5,142:2,-1:42,82,-1:14,142:8,-1:4,142,-1:4,142:5,32,142:9,-1,142:2," +
"-1:2,142:5,-1:5,142:2,-1:35,84,-1:21,142:8,-1:4,142,-1:4,142:2,135,142:12,-" +
"1,142:2,-1:2,142:5,-1:5,142:2,-1:22,136,-1:34,142:8,-1:4,142,-1:4,142:8,134" +
",142:6,-1,142:2,-1:2,142:5,-1:5,142:2,-1:8,86,-1:48,142:8,-1:4,142,-1:4,142" +
":4,91,142:10,-1,142:2,-1:2,142:5,-1:5,142:2,-1:32,88,-1:24,142:8,-1:4,142,-" +
"1:4,142:8,33,142:6,-1,142:2,-1:2,142:5,-1:5,142:2,-1:27,90,-1:29,142:8,-1:4" +
",142,-1:4,142,138,142:13,-1,142:2,-1:2,142:5,-1:5,142:2,-1:23,92,-1:33,142:" +
"8,-1:4,142,-1:4,142:13,137,142,-1,142:2,-1:2,142:5,-1:5,142:2,-1:9,96,-1:47" +
",142:8,-1:4,142,-1:4,142:6,99,142:8,-1,142:2,-1:2,142:5,-1:5,142:2,-1:29,13" +
"9,-1:27,142:8,-1:4,142,-1:4,142:14,101,-1,142:2,-1:2,142:5,-1:5,142:2,-1:31" +
",98,-1:25,142:8,-1:4,142,-1:4,142:10,103,142:4,-1,142:2,-1:2,142:5,-1:5,142" +
":2,-1:25,100,-1:31,142:8,-1:4,142,-1:4,142:11,140,142:3,-1,142:2,-1:2,142:5" +
",-1:5,142:2,-1:35,102,-1:21,142:8,-1:4,142,-1:4,142:5,36,142:9,-1,142:2,-1:" +
"2,142:5,-1:5,142:2,-1:10,104,-1:46,142:8,-1:4,142,-1:4,37,142:14,-1,142:2,-" +
"1:2,142:5,-1:5,142:2,-1:33,108,-1:23,142:8,-1:4,142,-1:4,142:8,109,142:6,-1" +
",142:2,-1:2,142:5,-1:5,142:2,-1:24,110,-1:32,142:8,-1:4,142,-1:4,142:4,111," +
"142:10,-1,142:2,-1:2,142:5,-1:5,142:2,-1:27,112,-1:29,142:8,-1:4,142,-1:4,1" +
"42:15,-1,142:2,-1:2,142,113,142:3,-1:5,142:2,-1:8,114,-1:48,142:8,-1:4,142," +
"-1:4,142:6,115,142:8,-1,142:2,-1:2,142:5,-1:5,142:2,-1:26,41,-1:30,142:8,-1" +
":4,142,-1:4,142,38,142:13,-1,142:2,-1:2,142:5,-1:5,142:2,-1:27,42,-1:29,142" +
":8,-1:4,142,-1:4,142,39,142:13,-1,142:2,-1:2,142:5,-1:5,142:2,-1:22,116,-1:" +
"34,142:8,-1:4,142,-1:4,142:2,117,142:12,-1,142:2,-1:2,142:5,-1:5,142:2,-1:2" +
"3,118,-1:33,142:8,-1:4,142,-1:4,142:4,40,142:10,-1,142:2,-1:2,142:5,-1:5,14" +
"2:2,-1:11,120,-1:45,142:8,-1:4,142,-1:4,142:4,119,142:10,-1,142:2,-1:2,142:" +
"5,-1:5,142:2,-1:26,45,-1:30,142:8,-1:4,142,-1:4,142:15,-1,142:2,-1:2,142:2," +
"123,142:2,-1:5,142:2,-1:43,122,-1:13,142:8,-1:4,142,-1:4,142:2,124,142:12,-" +
"1,142:2,-1:2,142:5,-1:5,142:2,-1:5,46,-1:51,142:8,-1:4,142,-1:4,142:8,125,1" +
"42:6,-1,142:2,-1:2,142:5,-1:5,142:2,-1:22,47,-1:34,142:8,-1:4,142,-1:4,142," +
"43,142:13,-1,142:2,-1:2,142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142,44,14" +
"2:13,-1,142:2,-1:2,142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:14,126,-1," +
"142:2,-1:2,142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:4,127,142:10,-1,14" +
"2:2,-1:2,142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:3,128,142:11,-1,142:" +
"2,-1:2,142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:6,129,142:8,-1,142:2,-" +
"1:2,142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:2,130,142:12,-1,142:2,-1:" +
"2,142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:11,131,142:3,-1,142:2,-1:2," +
"142:5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:6,48,142:8,-1,142:2,-1:2,142:" +
"5,-1:5,142:2,-1:4,142:8,-1:4,142,-1:4,142:8,77,142:6,-1,142:2,-1:2,142:5,-1" +
":5,142:2,-1:4,142:8,-1:4,142,-1:4,142:2,93,142:12,-1,142:2,-1:2,142:5,-1:5," +
"142:2,-1:4,142:8,-1:4,142,-1:4,142:4,97,142:10,-1,142:2,-1:2,142:5,-1:5,142" +
":2,-1:4,142:8,-1:4,142,-1:4,142,95,142:13,-1,142:2,-1:2,142:5,-1:5,142:2,-1" +
":23,94,-1:33,142:8,-1:4,142,-1:4,142:14,107,-1,142:2,-1:2,142:5,-1:5,142:2," +
"-1:4,142:8,-1:4,142,-1:4,142:10,105,142:4,-1,142:2,-1:2,142:5,-1:5,142:2,-1" +
":25,106,-1:31,142:8,-1:4,142,-1:4,142:8,141,142:6,-1,142:2,-1:2,142:5,-1:5," +
"142:2,-1:4,142:8,-1:4,142,-1:4,142:4,121,142:10,-1,142:2,-1:2,142:5,-1:5,14" +
"2:2,-1:4,142:8,-1:4,142,-1:4,142,81,142:13,-1,142:2,-1:2,142:5,-1:5,142:2,-" +
"1:4,142:8,-1:4,142,-1:4,142,83,142:13,-1,142:2,-1:2,142:5,-1:5,142:2,-1:4,1" +
"42:8,-1:4,142,-1:4,142:4,85,142:10,-1,142:2,-1:2,142:5,-1:5,142:2,-1:4,142:" +
"8,-1:4,142,-1:4,142,133,142:13,-1,142:2,-1:2,142:5,-1:5,142:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	return ops.unidadEOF();
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return ops.unidadAccesoArray();}
					case -3:
						break;
					case 3:
						{return ops.unidadCierreAcceso();}
					case -4:
						break;
					case 4:
						{return ops.unidadSep();}
					case -5:
						break;
					case 5:
						{return ops.unidadIdV();}
					case -6:
						break;
					case 6:
						//{ErrorLexico.errorLexico(fila(), lexema());}
					case -7:
						break;
					case 7:
						{}
					case -8:
						break;
					case 8:
						{return ops.unidadNumeroEntero();}
					case -9:
						break;
					case 9:
						{return ops.unidadMayor();}
					case -10:
						break;
					case 10:
						{return ops.unidadMenor();}
					case -11:
						break;
					case 11:
						{return ops.unidadConjuncion();}
					case -12:
						break;
					case 12:
						{return ops.unidadDisyuncion();}
					case -13:
						break;
					case 13:
						{return ops.unidadWildcard();}
					case -14:
						break;
					case 14:
						{return ops.unidadParentesisApertura();}
					case -15:
						break;
					case 15:
						{return ops.unidadParentesisCierre();}
					case -16:
						break;
					case 16:
						{return ops.unidadSeparadorDimensiones();}
					case -17:
						break;
					case 17:
						{return ops.unidadEntradaTipadoFuncion();}
					case -18:
						break;
					case 18:
						{return ops.unidadCorcheteApertura();}
					case -19:
						break;
					case 19:
						{return ops.unidadCorcheteCierre();}
					case -20:
						break;
					case 20:
						{return ops.unidadFinSentencia();}
					case -21:
						break;
					case 21:
						{return ops.unidadSalidaTipadoFuncion();}
					case -22:
						break;
					case 22:
						{return ops.unidadMenorOIgual();}
					case -23:
						break;
					case 23:
						{return ops.unidadMayorOIgual();}
					case -24:
						break;
					case 24:
						{return ops.unidadOperadorAsignacion();}
					case -25:
						break;
					case 25:
						{return ops.unidadNegacion();}
					case -26:
						break;
					case 26:
						{return ops.unidadParametrosLlamada();}
					case -27:
						break;
					case 27:
						{return ops.unidadTrue();}
					case -28:
						break;
					case 28:
						{return ops.unidadFalse();}
					case -29:
						break;
					case 29:
						{return ops.unidadTipadoVar();}
					case -30:
						break;
					case 30:
						{return ops.unidadIgual();}
					case -31:
						break;
					case 31:
						{return ops.unidadDistinto();}
					case -32:
						break;
					case 32:
						{return ops.unidadOperadorMas();}
					case -33:
						break;
					case 33:
						{return ops.unidadOperadorPor();}
					case -34:
						break;
					case 34:
						{return ops.unidadDeclaracionVar();}
					case -35:
						break;
					case 35:
						{return ops.unidadSalidaImplFuncion();}
					case -36:
						break;
					case 36:
						{return ops.unidadOperadorMenos();}
					case -37:
						break;
					case 37:
						{return ops.unidadTipoArray();}
					case -38:
						break;
					case 38:
						{return ops.unidadTipoNull();}
					case -39:
						break;
					case 39:
						{return ops.unidadTipoEntero();}
					case -40:
						break;
					case 40:
						{return ops.unidadLlamadaFuncion();}
					case -41:
						break;
					case 41:
						{return ops.unidadWhile();}
					case -42:
						break;
					case 42:
						{return ops.unidadResultadoFuncion();}
					case -43:
						break;
					case 43:
						{return ops.unidadEntradaImplFuncion();}
					case -44:
						break;
					case 44:
						{return ops.unidadTipoBool();}
					case -45:
						break;
					case 45:
						{return ops.unidadPrincipioFuncion();}
					case -46:
						break;
					case 46:
						{}
					case -47:
						break;
					case 47:
						{return ops.unidadIf();}
					case -48:
						break;
					case 48:
						{return ops.unidadElse();}
					case -49:
						break;
					case 50:
						{return ops.unidadIdV();}
					case -50:
						break;
					case 51:
						//{ErrorLexico.errorLexico(fila(), lexema());}
					case -51:
						break;
					case 52:
						{}
					case -52:
						break;
					case 54:
						{return ops.unidadIdV();}
					case -53:
						break;
					case 55:
						//{ErrorLexico.errorLexico(fila(), lexema());}
					case -54:
						break;
					case 57:
						{return ops.unidadIdV();}
					case -55:
						break;
					case 59:
						{return ops.unidadIdV();}
					case -56:
						break;
					case 61:
						{return ops.unidadIdV();}
					case -57:
						break;
					case 63:
						{return ops.unidadIdV();}
					case -58:
						break;
					case 65:
						{return ops.unidadIdV();}
					case -59:
						break;
					case 67:
						{return ops.unidadIdV();}
					case -60:
						break;
					case 69:
						{return ops.unidadIdV();}
					case -61:
						break;
					case 71:
						{return ops.unidadIdV();}
					case -62:
						break;
					case 73:
						{return ops.unidadIdV();}
					case -63:
						break;
					case 75:
						{return ops.unidadIdV();}
					case -64:
						break;
					case 77:
						{return ops.unidadIdV();}
					case -65:
						break;
					case 79:
						{return ops.unidadIdV();}
					case -66:
						break;
					case 81:
						{return ops.unidadIdV();}
					case -67:
						break;
					case 83:
						{return ops.unidadIdV();}
					case -68:
						break;
					case 85:
						{return ops.unidadIdV();}
					case -69:
						break;
					case 87:
						{return ops.unidadIdV();}
					case -70:
						break;
					case 89:
						{return ops.unidadIdV();}
					case -71:
						break;
					case 91:
						{return ops.unidadIdV();}
					case -72:
						break;
					case 93:
						{return ops.unidadIdV();}
					case -73:
						break;
					case 95:
						{return ops.unidadIdV();}
					case -74:
						break;
					case 97:
						{return ops.unidadIdV();}
					case -75:
						break;
					case 99:
						{return ops.unidadIdV();}
					case -76:
						break;
					case 101:
						{return ops.unidadIdV();}
					case -77:
						break;
					case 103:
						{return ops.unidadIdV();}
					case -78:
						break;
					case 105:
						{return ops.unidadIdV();}
					case -79:
						break;
					case 107:
						{return ops.unidadIdV();}
					case -80:
						break;
					case 109:
						{return ops.unidadIdV();}
					case -81:
						break;
					case 111:
						{return ops.unidadIdV();}
					case -82:
						break;
					case 113:
						{return ops.unidadIdV();}
					case -83:
						break;
					case 115:
						{return ops.unidadIdV();}
					case -84:
						break;
					case 117:
						{return ops.unidadIdV();}
					case -85:
						break;
					case 119:
						{return ops.unidadIdV();}
					case -86:
						break;
					case 121:
						{return ops.unidadIdV();}
					case -87:
						break;
					case 123:
						{return ops.unidadIdV();}
					case -88:
						break;
					case 124:
						{return ops.unidadIdV();}
					case -89:
						break;
					case 125:
						{return ops.unidadIdV();}
					case -90:
						break;
					case 126:
						{return ops.unidadIdV();}
					case -91:
						break;
					case 127:
						{return ops.unidadIdV();}
					case -92:
						break;
					case 128:
						{return ops.unidadIdV();}
					case -93:
						break;
					case 129:
						{return ops.unidadIdV();}
					case -94:
						break;
					case 130:
						{return ops.unidadIdV();}
					case -95:
						break;
					case 131:
						{return ops.unidadIdV();}
					case -96:
						break;
					case 132:
						{return ops.unidadIdV();}
					case -97:
						break;
					case 133:
						{return ops.unidadIdV();}
					case -98:
						break;
					case 134:
						{return ops.unidadIdV();}
					case -99:
						break;
					case 135:
						{return ops.unidadIdV();}
					case -100:
						break;
					case 137:
						{return ops.unidadIdV();}
					case -101:
						break;
					case 138:
						{return ops.unidadIdV();}
					case -102:
						break;
					case 140:
						{return ops.unidadIdV();}
					case -103:
						break;
					case 141:
						{return ops.unidadIdV();}
					case -104:
						break;
					case 142:
						{return ops.unidadIdV();}
					case -105:
						break;
					case 143:
						{return ops.unidadIdV();}
					case -106:
						break;
					case 144:
						{return ops.unidadIdV();}
					case -107:
						break;
					case 145:
						{return ops.unidadIdV();}
					case -108:
						break;
					case 146:
						{return ops.unidadIdV();}
					case -109:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
