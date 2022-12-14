package ru.touchin.roboswag.textprocessing.pcre.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PCRELexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Quoted=1, BlockQuoted=2, BellChar=3, ControlChar=4, EscapeChar=5, FormFeed=6, 
		NewLine=7, CarriageReturn=8, Tab=9, Backslash=10, HexChar=11, Dot=12, 
		OneDataUnit=13, DecimalDigit=14, NotDecimalDigit=15, HorizontalWhiteSpace=16, 
		NotHorizontalWhiteSpace=17, NotNewLine=18, CharWithProperty=19, CharWithoutProperty=20, 
		NewLineSequence=21, WhiteSpace=22, NotWhiteSpace=23, VerticalWhiteSpace=24, 
		NotVerticalWhiteSpace=25, WordChar=26, NotWordChar=27, ExtendedUnicodeChar=28, 
		CharacterClassStart=29, CharacterClassEnd=30, Caret=31, Hyphen=32, POSIXNamedSet=33, 
		POSIXNegatedNamedSet=34, QuestionMark=35, Plus=36, Star=37, OpenBrace=38, 
		CloseBrace=39, Comma=40, WordBoundary=41, NonWordBoundary=42, StartOfSubject=43, 
		EndOfSubjectOrLine=44, EndOfSubjectOrLineEndOfSubject=45, EndOfSubject=46, 
		PreviousMatchInSubject=47, ResetStartMatch=48, SubroutineOrNamedReferenceStartG=49, 
		NamedReferenceStartK=50, Pipe=51, OpenParen=52, CloseParen=53, LessThan=54, 
		GreaterThan=55, SingleQuote=56, Underscore=57, Colon=58, Hash=59, Equals=60, 
		Exclamation=61, Ampersand=62, ALC=63, BLC=64, CLC=65, DLC=66, ELC=67, 
		FLC=68, GLC=69, HLC=70, ILC=71, JLC=72, KLC=73, LLC=74, MLC=75, NLC=76, 
		OLC=77, PLC=78, QLC=79, RLC=80, SLC=81, TLC=82, ULC=83, VLC=84, WLC=85, 
		XLC=86, YLC=87, ZLC=88, AUC=89, BUC=90, CUC=91, DUC=92, EUC=93, FUC=94, 
		GUC=95, HUC=96, IUC=97, JUC=98, KUC=99, LUC=100, MUC=101, NUC=102, OUC=103, 
		PUC=104, QUC=105, RUC=106, SUC=107, TUC=108, UUC=109, VUC=110, WUC=111, 
		XUC=112, YUC=113, ZUC=114, D1=115, D2=116, D3=117, D4=118, D5=119, D6=120, 
		D7=121, D8=122, D9=123, D0=124, OtherChar=125;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Quoted", "BlockQuoted", "BellChar", "ControlChar", "EscapeChar", "FormFeed", 
			"NewLine", "CarriageReturn", "Tab", "Backslash", "HexChar", "Dot", "OneDataUnit", 
			"DecimalDigit", "NotDecimalDigit", "HorizontalWhiteSpace", "NotHorizontalWhiteSpace", 
			"NotNewLine", "CharWithProperty", "CharWithoutProperty", "NewLineSequence", 
			"WhiteSpace", "NotWhiteSpace", "VerticalWhiteSpace", "NotVerticalWhiteSpace", 
			"WordChar", "NotWordChar", "ExtendedUnicodeChar", "CharacterClassStart", 
			"CharacterClassEnd", "Caret", "Hyphen", "POSIXNamedSet", "POSIXNegatedNamedSet", 
			"QuestionMark", "Plus", "Star", "OpenBrace", "CloseBrace", "Comma", "WordBoundary", 
			"NonWordBoundary", "StartOfSubject", "EndOfSubjectOrLine", "EndOfSubjectOrLineEndOfSubject", 
			"EndOfSubject", "PreviousMatchInSubject", "ResetStartMatch", "SubroutineOrNamedReferenceStartG", 
			"NamedReferenceStartK", "Pipe", "OpenParen", "CloseParen", "LessThan", 
			"GreaterThan", "SingleQuote", "Underscore", "Colon", "Hash", "Equals", 
			"Exclamation", "Ampersand", "ALC", "BLC", "CLC", "DLC", "ELC", "FLC", 
			"GLC", "HLC", "ILC", "JLC", "KLC", "LLC", "MLC", "NLC", "OLC", "PLC", 
			"QLC", "RLC", "SLC", "TLC", "ULC", "VLC", "WLC", "XLC", "YLC", "ZLC", 
			"AUC", "BUC", "CUC", "DUC", "EUC", "FUC", "GUC", "HUC", "IUC", "JUC", 
			"KUC", "LUC", "MUC", "NUC", "OUC", "PUC", "QUC", "RUC", "SUC", "TUC", 
			"UUC", "VUC", "WUC", "XUC", "YUC", "ZUC", "D1", "D2", "D3", "D4", "D5", 
			"D6", "D7", "D8", "D9", "D0", "OtherChar", "UnderscoreAlphaNumerics", 
			"AlphaNumerics", "AlphaNumeric", "NonAlphaNumeric", "HexDigit", "ASCII"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'\\a'", null, "'\\e'", "'\\f'", "'\\n'", "'\\r'", 
			"'\\t'", "'\\'", null, "'.'", "'\\C'", "'\\d'", "'\\D'", "'\\h'", "'\\H'", 
			"'\\N'", null, null, "'\\R'", "'\\s'", "'\\S'", "'\\v'", "'\\V'", "'\\w'", 
			"'\\W'", "'\\X'", "'['", "']'", "'^'", "'-'", null, null, "'?'", "'+'", 
			"'*'", "'{'", "'}'", "','", "'\\b'", "'\\B'", "'\\A'", "'$'", "'\\Z'", 
			"'\\z'", "'\\G'", "'\\K'", "'\\g'", "'\\k'", "'|'", "'('", "')'", "'<'", 
			"'>'", "'''", "'_'", "':'", "'#'", "'='", "'!'", "'&'", "'a'", "'b'", 
			"'c'", "'d'", "'e'", "'f'", "'g'", "'h'", "'i'", "'j'", "'k'", "'l'", 
			"'m'", "'n'", "'o'", "'p'", "'q'", "'r'", "'s'", "'t'", "'u'", "'v'", 
			"'w'", "'x'", "'y'", "'z'", "'A'", "'B'", "'C'", "'D'", "'E'", "'F'", 
			"'G'", "'H'", "'I'", "'J'", "'K'", "'L'", "'M'", "'N'", "'O'", "'P'", 
			"'Q'", "'R'", "'S'", "'T'", "'U'", "'V'", "'W'", "'X'", "'Y'", "'Z'", 
			"'1'", "'2'", "'3'", "'4'", "'5'", "'6'", "'7'", "'8'", "'9'", "'0'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Quoted", "BlockQuoted", "BellChar", "ControlChar", "EscapeChar", 
			"FormFeed", "NewLine", "CarriageReturn", "Tab", "Backslash", "HexChar", 
			"Dot", "OneDataUnit", "DecimalDigit", "NotDecimalDigit", "HorizontalWhiteSpace", 
			"NotHorizontalWhiteSpace", "NotNewLine", "CharWithProperty", "CharWithoutProperty", 
			"NewLineSequence", "WhiteSpace", "NotWhiteSpace", "VerticalWhiteSpace", 
			"NotVerticalWhiteSpace", "WordChar", "NotWordChar", "ExtendedUnicodeChar", 
			"CharacterClassStart", "CharacterClassEnd", "Caret", "Hyphen", "POSIXNamedSet", 
			"POSIXNegatedNamedSet", "QuestionMark", "Plus", "Star", "OpenBrace", 
			"CloseBrace", "Comma", "WordBoundary", "NonWordBoundary", "StartOfSubject", 
			"EndOfSubjectOrLine", "EndOfSubjectOrLineEndOfSubject", "EndOfSubject", 
			"PreviousMatchInSubject", "ResetStartMatch", "SubroutineOrNamedReferenceStartG", 
			"NamedReferenceStartK", "Pipe", "OpenParen", "CloseParen", "LessThan", 
			"GreaterThan", "SingleQuote", "Underscore", "Colon", "Hash", "Equals", 
			"Exclamation", "Ampersand", "ALC", "BLC", "CLC", "DLC", "ELC", "FLC", 
			"GLC", "HLC", "ILC", "JLC", "KLC", "LLC", "MLC", "NLC", "OLC", "PLC", 
			"QLC", "RLC", "SLC", "TLC", "ULC", "VLC", "WLC", "XLC", "YLC", "ZLC", 
			"AUC", "BUC", "CUC", "DUC", "EUC", "FUC", "GUC", "HUC", "IUC", "JUC", 
			"KUC", "LUC", "MUC", "NUC", "OUC", "PUC", "QUC", "RUC", "SUC", "TUC", 
			"UUC", "VUC", "WUC", "XUC", "YUC", "ZUC", "D1", "D2", "D3", "D4", "D5", 
			"D6", "D7", "D8", "D9", "D0", "OtherChar"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public PCRELexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PCRE.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\177\u026b\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4"+
		"I\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\t"+
		"T\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_"+
		"\4`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k"+
		"\tk\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv"+
		"\4w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t"+
		"\u0080\4\u0081\t\u0081\4\u0082\t\u0082\4\u0083\t\u0083\4\u0084\t\u0084"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3\u0111\n\3\f\3\16\3\u0114\13\3\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5\u0120\n\5\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\6\f\u013d\n\f\r\f\16\f\u013e\3\f\3\f\5\f\u0143\n\f\3"+
		"\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35"+
		"\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)"+
		"\3)\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60"+
		"\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\65\3\65\3\66"+
		"\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@"+
		"\3A\3A\3B\3B\3C\3C\3D\3D\3E\3E\3F\3F\3G\3G\3H\3H\3I\3I\3J\3J\3K\3K\3L"+
		"\3L\3M\3M\3N\3N\3O\3O\3P\3P\3Q\3Q\3R\3R\3S\3S\3T\3T\3U\3U\3V\3V\3W\3W"+
		"\3X\3X\3Y\3Y\3Z\3Z\3[\3[\3\\\3\\\3]\3]\3^\3^\3_\3_\3`\3`\3a\3a\3b\3b\3"+
		"c\3c\3d\3d\3e\3e\3f\3f\3g\3g\3h\3h\3i\3i\3j\3j\3k\3k\3l\3l\3m\3m\3n\3"+
		"n\3o\3o\3p\3p\3q\3q\3r\3r\3s\3s\3t\3t\3u\3u\3v\3v\3w\3w\3x\3x\3y\3y\3"+
		"z\3z\3{\3{\3|\3|\3}\3}\3~\3~\3\177\3\177\6\177\u025b\n\177\r\177\16\177"+
		"\u025c\3\u0080\6\u0080\u0260\n\u0080\r\u0080\16\u0080\u0261\3\u0081\3"+
		"\u0081\3\u0082\3\u0082\3\u0083\3\u0083\3\u0084\3\u0084\3\u0112\2\u0085"+
		"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37"+
		"= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o"+
		"9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F\u008bG\u008dH"+
		"\u008fI\u0091J\u0093K\u0095L\u0097M\u0099N\u009bO\u009dP\u009fQ\u00a1"+
		"R\u00a3S\u00a5T\u00a7U\u00a9V\u00abW\u00adX\u00afY\u00b1Z\u00b3[\u00b5"+
		"\\\u00b7]\u00b9^\u00bb_\u00bd`\u00bfa\u00c1b\u00c3c\u00c5d\u00c7e\u00c9"+
		"f\u00cbg\u00cdh\u00cfi\u00d1j\u00d3k\u00d5l\u00d7m\u00d9n\u00dbo\u00dd"+
		"p\u00dfq\u00e1r\u00e3s\u00e5t\u00e7u\u00e9v\u00ebw\u00edx\u00efy\u00f1"+
		"z\u00f3{\u00f5|\u00f7}\u00f9~\u00fb\177\u00fd\2\u00ff\2\u0101\2\u0103"+
		"\2\u0105\2\u0107\2\3\2\5\5\2\62;C\\c|\5\2\62;CHch\3\2\2\u0081\2\u026b"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3"+
		"\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2"+
		"\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2"+
		"{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085"+
		"\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2"+
		"\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097"+
		"\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2"+
		"\2\2\u00a1\3\2\2\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2\2\2\u00a9"+
		"\3\2\2\2\2\u00ab\3\2\2\2\2\u00ad\3\2\2\2\2\u00af\3\2\2\2\2\u00b1\3\2\2"+
		"\2\2\u00b3\3\2\2\2\2\u00b5\3\2\2\2\2\u00b7\3\2\2\2\2\u00b9\3\2\2\2\2\u00bb"+
		"\3\2\2\2\2\u00bd\3\2\2\2\2\u00bf\3\2\2\2\2\u00c1\3\2\2\2\2\u00c3\3\2\2"+
		"\2\2\u00c5\3\2\2\2\2\u00c7\3\2\2\2\2\u00c9\3\2\2\2\2\u00cb\3\2\2\2\2\u00cd"+
		"\3\2\2\2\2\u00cf\3\2\2\2\2\u00d1\3\2\2\2\2\u00d3\3\2\2\2\2\u00d5\3\2\2"+
		"\2\2\u00d7\3\2\2\2\2\u00d9\3\2\2\2\2\u00db\3\2\2\2\2\u00dd\3\2\2\2\2\u00df"+
		"\3\2\2\2\2\u00e1\3\2\2\2\2\u00e3\3\2\2\2\2\u00e5\3\2\2\2\2\u00e7\3\2\2"+
		"\2\2\u00e9\3\2\2\2\2\u00eb\3\2\2\2\2\u00ed\3\2\2\2\2\u00ef\3\2\2\2\2\u00f1"+
		"\3\2\2\2\2\u00f3\3\2\2\2\2\u00f5\3\2\2\2\2\u00f7\3\2\2\2\2\u00f9\3\2\2"+
		"\2\2\u00fb\3\2\2\2\3\u0109\3\2\2\2\5\u010c\3\2\2\2\7\u0118\3\2\2\2\t\u011b"+
		"\3\2\2\2\13\u0121\3\2\2\2\r\u0124\3\2\2\2\17\u0127\3\2\2\2\21\u012a\3"+
		"\2\2\2\23\u012d\3\2\2\2\25\u0130\3\2\2\2\27\u0132\3\2\2\2\31\u0144\3\2"+
		"\2\2\33\u0146\3\2\2\2\35\u0149\3\2\2\2\37\u014c\3\2\2\2!\u014f\3\2\2\2"+
		"#\u0152\3\2\2\2%\u0155\3\2\2\2\'\u0158\3\2\2\2)\u015f\3\2\2\2+\u0166\3"+
		"\2\2\2-\u0169\3\2\2\2/\u016c\3\2\2\2\61\u016f\3\2\2\2\63\u0172\3\2\2\2"+
		"\65\u0175\3\2\2\2\67\u0178\3\2\2\29\u017b\3\2\2\2;\u017e\3\2\2\2=\u0180"+
		"\3\2\2\2?\u0182\3\2\2\2A\u0184\3\2\2\2C\u0186\3\2\2\2E\u018f\3\2\2\2G"+
		"\u0199\3\2\2\2I\u019b\3\2\2\2K\u019d\3\2\2\2M\u019f\3\2\2\2O\u01a1\3\2"+
		"\2\2Q\u01a3\3\2\2\2S\u01a5\3\2\2\2U\u01a8\3\2\2\2W\u01ab\3\2\2\2Y\u01ae"+
		"\3\2\2\2[\u01b0\3\2\2\2]\u01b3\3\2\2\2_\u01b6\3\2\2\2a\u01b9\3\2\2\2c"+
		"\u01bc\3\2\2\2e\u01bf\3\2\2\2g\u01c2\3\2\2\2i\u01c4\3\2\2\2k\u01c6\3\2"+
		"\2\2m\u01c8\3\2\2\2o\u01ca\3\2\2\2q\u01cc\3\2\2\2s\u01ce\3\2\2\2u\u01d0"+
		"\3\2\2\2w\u01d2\3\2\2\2y\u01d4\3\2\2\2{\u01d6\3\2\2\2}\u01d8\3\2\2\2\177"+
		"\u01da\3\2\2\2\u0081\u01dc\3\2\2\2\u0083\u01de\3\2\2\2\u0085\u01e0\3\2"+
		"\2\2\u0087\u01e2\3\2\2\2\u0089\u01e4\3\2\2\2\u008b\u01e6\3\2\2\2\u008d"+
		"\u01e8\3\2\2\2\u008f\u01ea\3\2\2\2\u0091\u01ec\3\2\2\2\u0093\u01ee\3\2"+
		"\2\2\u0095\u01f0\3\2\2\2\u0097\u01f2\3\2\2\2\u0099\u01f4\3\2\2\2\u009b"+
		"\u01f6\3\2\2\2\u009d\u01f8\3\2\2\2\u009f\u01fa\3\2\2\2\u00a1\u01fc\3\2"+
		"\2\2\u00a3\u01fe\3\2\2\2\u00a5\u0200\3\2\2\2\u00a7\u0202\3\2\2\2\u00a9"+
		"\u0204\3\2\2\2\u00ab\u0206\3\2\2\2\u00ad\u0208\3\2\2\2\u00af\u020a\3\2"+
		"\2\2\u00b1\u020c\3\2\2\2\u00b3\u020e\3\2\2\2\u00b5\u0210\3\2\2\2\u00b7"+
		"\u0212\3\2\2\2\u00b9\u0214\3\2\2\2\u00bb\u0216\3\2\2\2\u00bd\u0218\3\2"+
		"\2\2\u00bf\u021a\3\2\2\2\u00c1\u021c\3\2\2\2\u00c3\u021e\3\2\2\2\u00c5"+
		"\u0220\3\2\2\2\u00c7\u0222\3\2\2\2\u00c9\u0224\3\2\2\2\u00cb\u0226\3\2"+
		"\2\2\u00cd\u0228\3\2\2\2\u00cf\u022a\3\2\2\2\u00d1\u022c\3\2\2\2\u00d3"+
		"\u022e\3\2\2\2\u00d5\u0230\3\2\2\2\u00d7\u0232\3\2\2\2\u00d9\u0234\3\2"+
		"\2\2\u00db\u0236\3\2\2\2\u00dd\u0238\3\2\2\2\u00df\u023a\3\2\2\2\u00e1"+
		"\u023c\3\2\2\2\u00e3\u023e\3\2\2\2\u00e5\u0240\3\2\2\2\u00e7\u0242\3\2"+
		"\2\2\u00e9\u0244\3\2\2\2\u00eb\u0246\3\2\2\2\u00ed\u0248\3\2\2\2\u00ef"+
		"\u024a\3\2\2\2\u00f1\u024c\3\2\2\2\u00f3\u024e\3\2\2\2\u00f5\u0250\3\2"+
		"\2\2\u00f7\u0252\3\2\2\2\u00f9\u0254\3\2\2\2\u00fb\u0256\3\2\2\2\u00fd"+
		"\u025a\3\2\2\2\u00ff\u025f\3\2\2\2\u0101\u0263\3\2\2\2\u0103\u0265\3\2"+
		"\2\2\u0105\u0267\3\2\2\2\u0107\u0269\3\2\2\2\u0109\u010a\7^\2\2\u010a"+
		"\u010b\5\u0103\u0082\2\u010b\4\3\2\2\2\u010c\u010d\7^\2\2\u010d\u010e"+
		"\7S\2\2\u010e\u0112\3\2\2\2\u010f\u0111\13\2\2\2\u0110\u010f\3\2\2\2\u0111"+
		"\u0114\3\2\2\2\u0112\u0113\3\2\2\2\u0112\u0110\3\2\2\2\u0113\u0115\3\2"+
		"\2\2\u0114\u0112\3\2\2\2\u0115\u0116\7^\2\2\u0116\u0117\7G\2\2\u0117\6"+
		"\3\2\2\2\u0118\u0119\7^\2\2\u0119\u011a\7c\2\2\u011a\b\3\2\2\2\u011b\u011c"+
		"\7^\2\2\u011c\u011d\7e\2\2\u011d\u011f\3\2\2\2\u011e\u0120\5\u0107\u0084"+
		"\2\u011f\u011e\3\2\2\2\u011f\u0120\3\2\2\2\u0120\n\3\2\2\2\u0121\u0122"+
		"\7^\2\2\u0122\u0123\7g\2\2\u0123\f\3\2\2\2\u0124\u0125\7^\2\2\u0125\u0126"+
		"\7h\2\2\u0126\16\3\2\2\2\u0127\u0128\7^\2\2\u0128\u0129\7p\2\2\u0129\20"+
		"\3\2\2\2\u012a\u012b\7^\2\2\u012b\u012c\7t\2\2\u012c\22\3\2\2\2\u012d"+
		"\u012e\7^\2\2\u012e\u012f\7v\2\2\u012f\24\3\2\2\2\u0130\u0131\7^\2\2\u0131"+
		"\26\3\2\2\2\u0132\u0133\7^\2\2\u0133\u0134\7z\2\2\u0134\u0142\3\2\2\2"+
		"\u0135\u0136\5\u0105\u0083\2\u0136\u0137\5\u0105\u0083\2\u0137\u0143\3"+
		"\2\2\2\u0138\u0139\7}\2\2\u0139\u013a\5\u0105\u0083\2\u013a\u013c\5\u0105"+
		"\u0083\2\u013b\u013d\5\u0105\u0083\2\u013c\u013b\3\2\2\2\u013d\u013e\3"+
		"\2\2\2\u013e\u013c\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u0140\3\2\2\2\u0140"+
		"\u0141\7\177\2\2\u0141\u0143\3\2\2\2\u0142\u0135\3\2\2\2\u0142\u0138\3"+
		"\2\2\2\u0143\30\3\2\2\2\u0144\u0145\7\60\2\2\u0145\32\3\2\2\2\u0146\u0147"+
		"\7^\2\2\u0147\u0148\7E\2\2\u0148\34\3\2\2\2\u0149\u014a\7^\2\2\u014a\u014b"+
		"\7f\2\2\u014b\36\3\2\2\2\u014c\u014d\7^\2\2\u014d\u014e\7F\2\2\u014e "+
		"\3\2\2\2\u014f\u0150\7^\2\2\u0150\u0151\7j\2\2\u0151\"\3\2\2\2\u0152\u0153"+
		"\7^\2\2\u0153\u0154\7J\2\2\u0154$\3\2\2\2\u0155\u0156\7^\2\2\u0156\u0157"+
		"\7P\2\2\u0157&\3\2\2\2\u0158\u0159\7^\2\2\u0159\u015a\7r\2\2\u015a\u015b"+
		"\7}\2\2\u015b\u015c\3\2\2\2\u015c\u015d\5\u00fd\177\2\u015d\u015e\7\177"+
		"\2\2\u015e(\3\2\2\2\u015f\u0160\7^\2\2\u0160\u0161\7R\2\2\u0161\u0162"+
		"\7}\2\2\u0162\u0163\3\2\2\2\u0163\u0164\5\u00fd\177\2\u0164\u0165\7\177"+
		"\2\2\u0165*\3\2\2\2\u0166\u0167\7^\2\2\u0167\u0168\7T\2\2\u0168,\3\2\2"+
		"\2\u0169\u016a\7^\2\2\u016a\u016b\7u\2\2\u016b.\3\2\2\2\u016c\u016d\7"+
		"^\2\2\u016d\u016e\7U\2\2\u016e\60\3\2\2\2\u016f\u0170\7^\2\2\u0170\u0171"+
		"\7x\2\2\u0171\62\3\2\2\2\u0172\u0173\7^\2\2\u0173\u0174\7X\2\2\u0174\64"+
		"\3\2\2\2\u0175\u0176\7^\2\2\u0176\u0177\7y\2\2\u0177\66\3\2\2\2\u0178"+
		"\u0179\7^\2\2\u0179\u017a\7Y\2\2\u017a8\3\2\2\2\u017b\u017c\7^\2\2\u017c"+
		"\u017d\7Z\2\2\u017d:\3\2\2\2\u017e\u017f\7]\2\2\u017f<\3\2\2\2\u0180\u0181"+
		"\7_\2\2\u0181>\3\2\2\2\u0182\u0183\7`\2\2\u0183@\3\2\2\2\u0184\u0185\7"+
		"/\2\2\u0185B\3\2\2\2\u0186\u0187\7]\2\2\u0187\u0188\7]\2\2\u0188\u0189"+
		"\7<\2\2\u0189\u018a\3\2\2\2\u018a\u018b\5\u00ff\u0080\2\u018b\u018c\7"+
		"<\2\2\u018c\u018d\7_\2\2\u018d\u018e\7_\2\2\u018eD\3\2\2\2\u018f\u0190"+
		"\7]\2\2\u0190\u0191\7]\2\2\u0191\u0192\7<\2\2\u0192\u0193\7`\2\2\u0193"+
		"\u0194\3\2\2\2\u0194\u0195\5\u00ff\u0080\2\u0195\u0196\7<\2\2\u0196\u0197"+
		"\7_\2\2\u0197\u0198\7_\2\2\u0198F\3\2\2\2\u0199\u019a\7A\2\2\u019aH\3"+
		"\2\2\2\u019b\u019c\7-\2\2\u019cJ\3\2\2\2\u019d\u019e\7,\2\2\u019eL\3\2"+
		"\2\2\u019f\u01a0\7}\2\2\u01a0N\3\2\2\2\u01a1\u01a2\7\177\2\2\u01a2P\3"+
		"\2\2\2\u01a3\u01a4\7.\2\2\u01a4R\3\2\2\2\u01a5\u01a6\7^\2\2\u01a6\u01a7"+
		"\7d\2\2\u01a7T\3\2\2\2\u01a8\u01a9\7^\2\2\u01a9\u01aa\7D\2\2\u01aaV\3"+
		"\2\2\2\u01ab\u01ac\7^\2\2\u01ac\u01ad\7C\2\2\u01adX\3\2\2\2\u01ae\u01af"+
		"\7&\2\2\u01afZ\3\2\2\2\u01b0\u01b1\7^\2\2\u01b1\u01b2\7\\\2\2\u01b2\\"+
		"\3\2\2\2\u01b3\u01b4\7^\2\2\u01b4\u01b5\7|\2\2\u01b5^\3\2\2\2\u01b6\u01b7"+
		"\7^\2\2\u01b7\u01b8\7I\2\2\u01b8`\3\2\2\2\u01b9\u01ba\7^\2\2\u01ba\u01bb"+
		"\7M\2\2\u01bbb\3\2\2\2\u01bc\u01bd\7^\2\2\u01bd\u01be\7i\2\2\u01bed\3"+
		"\2\2\2\u01bf\u01c0\7^\2\2\u01c0\u01c1\7m\2\2\u01c1f\3\2\2\2\u01c2\u01c3"+
		"\7~\2\2\u01c3h\3\2\2\2\u01c4\u01c5\7*\2\2\u01c5j\3\2\2\2\u01c6\u01c7\7"+
		"+\2\2\u01c7l\3\2\2\2\u01c8\u01c9\7>\2\2\u01c9n\3\2\2\2\u01ca\u01cb\7@"+
		"\2\2\u01cbp\3\2\2\2\u01cc\u01cd\7)\2\2\u01cdr\3\2\2\2\u01ce\u01cf\7a\2"+
		"\2\u01cft\3\2\2\2\u01d0\u01d1\7<\2\2\u01d1v\3\2\2\2\u01d2\u01d3\7%\2\2"+
		"\u01d3x\3\2\2\2\u01d4\u01d5\7?\2\2\u01d5z\3\2\2\2\u01d6\u01d7\7#\2\2\u01d7"+
		"|\3\2\2\2\u01d8\u01d9\7(\2\2\u01d9~\3\2\2\2\u01da\u01db\7c\2\2\u01db\u0080"+
		"\3\2\2\2\u01dc\u01dd\7d\2\2\u01dd\u0082\3\2\2\2\u01de\u01df\7e\2\2\u01df"+
		"\u0084\3\2\2\2\u01e0\u01e1\7f\2\2\u01e1\u0086\3\2\2\2\u01e2\u01e3\7g\2"+
		"\2\u01e3\u0088\3\2\2\2\u01e4\u01e5\7h\2\2\u01e5\u008a\3\2\2\2\u01e6\u01e7"+
		"\7i\2\2\u01e7\u008c\3\2\2\2\u01e8\u01e9\7j\2\2\u01e9\u008e\3\2\2\2\u01ea"+
		"\u01eb\7k\2\2\u01eb\u0090\3\2\2\2\u01ec\u01ed\7l\2\2\u01ed\u0092\3\2\2"+
		"\2\u01ee\u01ef\7m\2\2\u01ef\u0094\3\2\2\2\u01f0\u01f1\7n\2\2\u01f1\u0096"+
		"\3\2\2\2\u01f2\u01f3\7o\2\2\u01f3\u0098\3\2\2\2\u01f4\u01f5\7p\2\2\u01f5"+
		"\u009a\3\2\2\2\u01f6\u01f7\7q\2\2\u01f7\u009c\3\2\2\2\u01f8\u01f9\7r\2"+
		"\2\u01f9\u009e\3\2\2\2\u01fa\u01fb\7s\2\2\u01fb\u00a0\3\2\2\2\u01fc\u01fd"+
		"\7t\2\2\u01fd\u00a2\3\2\2\2\u01fe\u01ff\7u\2\2\u01ff\u00a4\3\2\2\2\u0200"+
		"\u0201\7v\2\2\u0201\u00a6\3\2\2\2\u0202\u0203\7w\2\2\u0203\u00a8\3\2\2"+
		"\2\u0204\u0205\7x\2\2\u0205\u00aa\3\2\2\2\u0206\u0207\7y\2\2\u0207\u00ac"+
		"\3\2\2\2\u0208\u0209\7z\2\2\u0209\u00ae\3\2\2\2\u020a\u020b\7{\2\2\u020b"+
		"\u00b0\3\2\2\2\u020c\u020d\7|\2\2\u020d\u00b2\3\2\2\2\u020e\u020f\7C\2"+
		"\2\u020f\u00b4\3\2\2\2\u0210\u0211\7D\2\2\u0211\u00b6\3\2\2\2\u0212\u0213"+
		"\7E\2\2\u0213\u00b8\3\2\2\2\u0214\u0215\7F\2\2\u0215\u00ba\3\2\2\2\u0216"+
		"\u0217\7G\2\2\u0217\u00bc\3\2\2\2\u0218\u0219\7H\2\2\u0219\u00be\3\2\2"+
		"\2\u021a\u021b\7I\2\2\u021b\u00c0\3\2\2\2\u021c\u021d\7J\2\2\u021d\u00c2"+
		"\3\2\2\2\u021e\u021f\7K\2\2\u021f\u00c4\3\2\2\2\u0220\u0221\7L\2\2\u0221"+
		"\u00c6\3\2\2\2\u0222\u0223\7M\2\2\u0223\u00c8\3\2\2\2\u0224\u0225\7N\2"+
		"\2\u0225\u00ca\3\2\2\2\u0226\u0227\7O\2\2\u0227\u00cc\3\2\2\2\u0228\u0229"+
		"\7P\2\2\u0229\u00ce\3\2\2\2\u022a\u022b\7Q\2\2\u022b\u00d0\3\2\2\2\u022c"+
		"\u022d\7R\2\2\u022d\u00d2\3\2\2\2\u022e\u022f\7S\2\2\u022f\u00d4\3\2\2"+
		"\2\u0230\u0231\7T\2\2\u0231\u00d6\3\2\2\2\u0232\u0233\7U\2\2\u0233\u00d8"+
		"\3\2\2\2\u0234\u0235\7V\2\2\u0235\u00da\3\2\2\2\u0236\u0237\7W\2\2\u0237"+
		"\u00dc\3\2\2\2\u0238\u0239\7X\2\2\u0239\u00de\3\2\2\2\u023a\u023b\7Y\2"+
		"\2\u023b\u00e0\3\2\2\2\u023c\u023d\7Z\2\2\u023d\u00e2\3\2\2\2\u023e\u023f"+
		"\7[\2\2\u023f\u00e4\3\2\2\2\u0240\u0241\7\\\2\2\u0241\u00e6\3\2\2\2\u0242"+
		"\u0243\7\63\2\2\u0243\u00e8\3\2\2\2\u0244\u0245\7\64\2\2\u0245\u00ea\3"+
		"\2\2\2\u0246\u0247\7\65\2\2\u0247\u00ec\3\2\2\2\u0248\u0249\7\66\2\2\u0249"+
		"\u00ee\3\2\2\2\u024a\u024b\7\67\2\2\u024b\u00f0\3\2\2\2\u024c\u024d\7"+
		"8\2\2\u024d\u00f2\3\2\2\2\u024e\u024f\79\2\2\u024f\u00f4\3\2\2\2\u0250"+
		"\u0251\7:\2\2\u0251\u00f6\3\2\2\2\u0252\u0253\7;\2\2\u0253\u00f8\3\2\2"+
		"\2\u0254\u0255\7\62\2\2\u0255\u00fa\3\2\2\2\u0256\u0257\13\2\2\2\u0257"+
		"\u00fc\3\2\2\2\u0258\u025b\7a\2\2\u0259\u025b\5\u0101\u0081\2\u025a\u0258"+
		"\3\2\2\2\u025a\u0259\3\2\2\2\u025b\u025c\3\2\2\2\u025c\u025a\3\2\2\2\u025c"+
		"\u025d\3\2\2\2\u025d\u00fe\3\2\2\2\u025e\u0260\5\u0101\u0081\2\u025f\u025e"+
		"\3\2\2\2\u0260\u0261\3\2\2\2\u0261\u025f\3\2\2\2\u0261\u0262\3\2\2\2\u0262"+
		"\u0100\3\2\2\2\u0263\u0264\t\2\2\2\u0264\u0102\3\2\2\2\u0265\u0266\n\2"+
		"\2\2\u0266\u0104\3\2\2\2\u0267\u0268\t\3\2\2\u0268\u0106\3\2\2\2\u0269"+
		"\u026a\t\4\2\2\u026a\u0108\3\2\2\2\n\2\u0112\u011f\u013e\u0142\u025a\u025c"+
		"\u0261\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}