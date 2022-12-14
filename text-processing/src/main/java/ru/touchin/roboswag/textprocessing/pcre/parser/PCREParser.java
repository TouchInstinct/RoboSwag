package ru.touchin.roboswag.textprocessing.pcre.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PCREParser extends Parser {
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
	public static final int
		RULE_parse = 0, RULE_alternation = 1, RULE_expr = 2, RULE_element = 3, 
		RULE_quantifier = 4, RULE_quantifier_type = 5, RULE_character_class = 6, 
		RULE_backreference = 7, RULE_backreference_or_octal = 8, RULE_capture = 9, 
		RULE_non_capture = 10, RULE_comment = 11, RULE_option = 12, RULE_option_flags = 13, 
		RULE_option_flag = 14, RULE_look_around = 15, RULE_subroutine_reference = 16, 
		RULE_conditional = 17, RULE_backtrack_control = 18, RULE_newline_convention = 19, 
		RULE_callout = 20, RULE_atom = 21, RULE_cc_atom = 22, RULE_shared_atom = 23, 
		RULE_literal = 24, RULE_cc_literal = 25, RULE_shared_literal = 26, RULE_number = 27, 
		RULE_octal_char = 28, RULE_octal_digit = 29, RULE_digits = 30, RULE_digit = 31, 
		RULE_name = 32, RULE_alpha_nums = 33, RULE_non_close_parens = 34, RULE_non_close_paren = 35, 
		RULE_letter = 36;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "alternation", "expr", "element", "quantifier", "quantifier_type", 
			"character_class", "backreference", "backreference_or_octal", "capture", 
			"non_capture", "comment", "option", "option_flags", "option_flag", "look_around", 
			"subroutine_reference", "conditional", "backtrack_control", "newline_convention", 
			"callout", "atom", "cc_atom", "shared_atom", "literal", "cc_literal", 
			"shared_literal", "number", "octal_char", "octal_digit", "digits", "digit", 
			"name", "alpha_nums", "non_close_parens", "non_close_paren", "letter"
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

	@Override
	public String getGrammarFileName() { return "PCRE.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PCREParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ParseContext extends ParserRuleContext {
		public AlternationContext alternation() {
			return getRuleContext(AlternationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(PCREParser.EOF, 0); }
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitParse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			alternation();
			setState(75);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlternationContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> Pipe() { return getTokens(PCREParser.Pipe); }
		public TerminalNode Pipe(int i) {
			return getToken(PCREParser.Pipe, i);
		}
		public AlternationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alternation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterAlternation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitAlternation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitAlternation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlternationContext alternation() throws RecognitionException {
		AlternationContext _localctx = new AlternationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_alternation);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			expr();
			setState(82);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(78);
					match(Pipe);
					setState(79);
					expr();
					}
					} 
				}
				setState(84);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Quoted) | (1L << BlockQuoted) | (1L << BellChar) | (1L << ControlChar) | (1L << EscapeChar) | (1L << FormFeed) | (1L << NewLine) | (1L << CarriageReturn) | (1L << Tab) | (1L << Backslash) | (1L << HexChar) | (1L << Dot) | (1L << OneDataUnit) | (1L << DecimalDigit) | (1L << NotDecimalDigit) | (1L << HorizontalWhiteSpace) | (1L << NotHorizontalWhiteSpace) | (1L << NotNewLine) | (1L << CharWithProperty) | (1L << CharWithoutProperty) | (1L << NewLineSequence) | (1L << WhiteSpace) | (1L << NotWhiteSpace) | (1L << VerticalWhiteSpace) | (1L << NotVerticalWhiteSpace) | (1L << WordChar) | (1L << NotWordChar) | (1L << ExtendedUnicodeChar) | (1L << CharacterClassStart) | (1L << CharacterClassEnd) | (1L << Caret) | (1L << Hyphen) | (1L << POSIXNamedSet) | (1L << POSIXNegatedNamedSet) | (1L << OpenBrace) | (1L << CloseBrace) | (1L << Comma) | (1L << WordBoundary) | (1L << NonWordBoundary) | (1L << StartOfSubject) | (1L << EndOfSubjectOrLine) | (1L << EndOfSubjectOrLineEndOfSubject) | (1L << EndOfSubject) | (1L << PreviousMatchInSubject) | (1L << ResetStartMatch) | (1L << SubroutineOrNamedReferenceStartG) | (1L << NamedReferenceStartK) | (1L << OpenParen) | (1L << LessThan) | (1L << GreaterThan) | (1L << SingleQuote) | (1L << Underscore) | (1L << Colon) | (1L << Hash) | (1L << Equals) | (1L << Exclamation) | (1L << Ampersand) | (1L << ALC))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)) | (1L << (OtherChar - 64)))) != 0)) {
				{
				{
				setState(85);
				element();
				}
				}
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public QuantifierContext quantifier() {
			return getRuleContext(QuantifierContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_element);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			atom();
			setState(93);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(92);
				quantifier();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantifierContext extends ParserRuleContext {
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public Quantifier_typeContext quantifier_type() {
			return getRuleContext(Quantifier_typeContext.class,0);
		}
		public TerminalNode Plus() { return getToken(PCREParser.Plus, 0); }
		public TerminalNode Star() { return getToken(PCREParser.Star, 0); }
		public TerminalNode OpenBrace() { return getToken(PCREParser.OpenBrace, 0); }
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public TerminalNode CloseBrace() { return getToken(PCREParser.CloseBrace, 0); }
		public TerminalNode Comma() { return getToken(PCREParser.Comma, 0); }
		public QuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitQuantifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantifierContext quantifier() throws RecognitionException {
		QuantifierContext _localctx = new QuantifierContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_quantifier);
		try {
			setState(119);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(95);
				match(QuestionMark);
				setState(96);
				quantifier_type();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				match(Plus);
				setState(98);
				quantifier_type();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(99);
				match(Star);
				setState(100);
				quantifier_type();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(101);
				match(OpenBrace);
				setState(102);
				number();
				setState(103);
				match(CloseBrace);
				setState(104);
				quantifier_type();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(106);
				match(OpenBrace);
				setState(107);
				number();
				setState(108);
				match(Comma);
				setState(109);
				match(CloseBrace);
				setState(110);
				quantifier_type();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(112);
				match(OpenBrace);
				setState(113);
				number();
				setState(114);
				match(Comma);
				setState(115);
				number();
				setState(116);
				match(CloseBrace);
				setState(117);
				quantifier_type();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Quantifier_typeContext extends ParserRuleContext {
		public TerminalNode Plus() { return getToken(PCREParser.Plus, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public Quantifier_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantifier_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterQuantifier_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitQuantifier_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitQuantifier_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Quantifier_typeContext quantifier_type() throws RecognitionException {
		Quantifier_typeContext _localctx = new Quantifier_typeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_quantifier_type);
		try {
			setState(124);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Plus:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				match(Plus);
				}
				break;
			case QuestionMark:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				match(QuestionMark);
				}
				break;
			case EOF:
			case Quoted:
			case BlockQuoted:
			case BellChar:
			case ControlChar:
			case EscapeChar:
			case FormFeed:
			case NewLine:
			case CarriageReturn:
			case Tab:
			case Backslash:
			case HexChar:
			case Dot:
			case OneDataUnit:
			case DecimalDigit:
			case NotDecimalDigit:
			case HorizontalWhiteSpace:
			case NotHorizontalWhiteSpace:
			case NotNewLine:
			case CharWithProperty:
			case CharWithoutProperty:
			case NewLineSequence:
			case WhiteSpace:
			case NotWhiteSpace:
			case VerticalWhiteSpace:
			case NotVerticalWhiteSpace:
			case WordChar:
			case NotWordChar:
			case ExtendedUnicodeChar:
			case CharacterClassStart:
			case CharacterClassEnd:
			case Caret:
			case Hyphen:
			case POSIXNamedSet:
			case POSIXNegatedNamedSet:
			case OpenBrace:
			case CloseBrace:
			case Comma:
			case WordBoundary:
			case NonWordBoundary:
			case StartOfSubject:
			case EndOfSubjectOrLine:
			case EndOfSubjectOrLineEndOfSubject:
			case EndOfSubject:
			case PreviousMatchInSubject:
			case ResetStartMatch:
			case SubroutineOrNamedReferenceStartG:
			case NamedReferenceStartK:
			case Pipe:
			case OpenParen:
			case CloseParen:
			case LessThan:
			case GreaterThan:
			case SingleQuote:
			case Underscore:
			case Colon:
			case Hash:
			case Equals:
			case Exclamation:
			case Ampersand:
			case ALC:
			case BLC:
			case CLC:
			case DLC:
			case ELC:
			case FLC:
			case GLC:
			case HLC:
			case ILC:
			case JLC:
			case KLC:
			case LLC:
			case MLC:
			case NLC:
			case OLC:
			case PLC:
			case QLC:
			case RLC:
			case SLC:
			case TLC:
			case ULC:
			case VLC:
			case WLC:
			case XLC:
			case YLC:
			case ZLC:
			case AUC:
			case BUC:
			case CUC:
			case DUC:
			case EUC:
			case FUC:
			case GUC:
			case HUC:
			case IUC:
			case JUC:
			case KUC:
			case LUC:
			case MUC:
			case NUC:
			case OUC:
			case PUC:
			case QUC:
			case RUC:
			case SUC:
			case TUC:
			case UUC:
			case VUC:
			case WUC:
			case XUC:
			case YUC:
			case ZUC:
			case D1:
			case D2:
			case D3:
			case D4:
			case D5:
			case D6:
			case D7:
			case D8:
			case D9:
			case D0:
			case OtherChar:
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Character_classContext extends ParserRuleContext {
		public TerminalNode CharacterClassStart() { return getToken(PCREParser.CharacterClassStart, 0); }
		public TerminalNode Caret() { return getToken(PCREParser.Caret, 0); }
		public List<TerminalNode> CharacterClassEnd() { return getTokens(PCREParser.CharacterClassEnd); }
		public TerminalNode CharacterClassEnd(int i) {
			return getToken(PCREParser.CharacterClassEnd, i);
		}
		public TerminalNode Hyphen() { return getToken(PCREParser.Hyphen, 0); }
		public List<Cc_atomContext> cc_atom() {
			return getRuleContexts(Cc_atomContext.class);
		}
		public Cc_atomContext cc_atom(int i) {
			return getRuleContext(Cc_atomContext.class,i);
		}
		public Character_classContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_character_class; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterCharacter_class(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitCharacter_class(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitCharacter_class(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Character_classContext character_class() throws RecognitionException {
		Character_classContext _localctx = new Character_classContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_character_class);
		int _la;
		try {
			setState(183);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				match(CharacterClassStart);
				setState(127);
				match(Caret);
				setState(128);
				match(CharacterClassEnd);
				setState(129);
				match(Hyphen);
				setState(131); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(130);
					cc_atom();
					}
					}
					setState(133); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Quoted) | (1L << BlockQuoted) | (1L << BellChar) | (1L << ControlChar) | (1L << EscapeChar) | (1L << FormFeed) | (1L << NewLine) | (1L << CarriageReturn) | (1L << Tab) | (1L << Backslash) | (1L << HexChar) | (1L << Dot) | (1L << DecimalDigit) | (1L << NotDecimalDigit) | (1L << HorizontalWhiteSpace) | (1L << NotHorizontalWhiteSpace) | (1L << NotNewLine) | (1L << CharWithProperty) | (1L << CharWithoutProperty) | (1L << NewLineSequence) | (1L << WhiteSpace) | (1L << NotWhiteSpace) | (1L << VerticalWhiteSpace) | (1L << NotVerticalWhiteSpace) | (1L << WordChar) | (1L << NotWordChar) | (1L << CharacterClassStart) | (1L << Caret) | (1L << Hyphen) | (1L << POSIXNamedSet) | (1L << POSIXNegatedNamedSet) | (1L << QuestionMark) | (1L << Plus) | (1L << Star) | (1L << OpenBrace) | (1L << CloseBrace) | (1L << Comma) | (1L << WordBoundary) | (1L << EndOfSubjectOrLine) | (1L << Pipe) | (1L << OpenParen) | (1L << CloseParen) | (1L << LessThan) | (1L << GreaterThan) | (1L << SingleQuote) | (1L << Underscore) | (1L << Colon) | (1L << Hash) | (1L << Equals) | (1L << Exclamation) | (1L << Ampersand) | (1L << ALC))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)) | (1L << (OtherChar - 64)))) != 0) );
				setState(135);
				match(CharacterClassEnd);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(137);
				match(CharacterClassStart);
				setState(138);
				match(Caret);
				setState(139);
				match(CharacterClassEnd);
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Quoted) | (1L << BlockQuoted) | (1L << BellChar) | (1L << ControlChar) | (1L << EscapeChar) | (1L << FormFeed) | (1L << NewLine) | (1L << CarriageReturn) | (1L << Tab) | (1L << Backslash) | (1L << HexChar) | (1L << Dot) | (1L << DecimalDigit) | (1L << NotDecimalDigit) | (1L << HorizontalWhiteSpace) | (1L << NotHorizontalWhiteSpace) | (1L << NotNewLine) | (1L << CharWithProperty) | (1L << CharWithoutProperty) | (1L << NewLineSequence) | (1L << WhiteSpace) | (1L << NotWhiteSpace) | (1L << VerticalWhiteSpace) | (1L << NotVerticalWhiteSpace) | (1L << WordChar) | (1L << NotWordChar) | (1L << CharacterClassStart) | (1L << Caret) | (1L << Hyphen) | (1L << POSIXNamedSet) | (1L << POSIXNegatedNamedSet) | (1L << QuestionMark) | (1L << Plus) | (1L << Star) | (1L << OpenBrace) | (1L << CloseBrace) | (1L << Comma) | (1L << WordBoundary) | (1L << EndOfSubjectOrLine) | (1L << Pipe) | (1L << OpenParen) | (1L << CloseParen) | (1L << LessThan) | (1L << GreaterThan) | (1L << SingleQuote) | (1L << Underscore) | (1L << Colon) | (1L << Hash) | (1L << Equals) | (1L << Exclamation) | (1L << Ampersand) | (1L << ALC))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)) | (1L << (OtherChar - 64)))) != 0)) {
					{
					{
					setState(140);
					cc_atom();
					}
					}
					setState(145);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(146);
				match(CharacterClassEnd);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(147);
				match(CharacterClassStart);
				setState(148);
				match(Caret);
				setState(150); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(149);
					cc_atom();
					}
					}
					setState(152); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Quoted) | (1L << BlockQuoted) | (1L << BellChar) | (1L << ControlChar) | (1L << EscapeChar) | (1L << FormFeed) | (1L << NewLine) | (1L << CarriageReturn) | (1L << Tab) | (1L << Backslash) | (1L << HexChar) | (1L << Dot) | (1L << DecimalDigit) | (1L << NotDecimalDigit) | (1L << HorizontalWhiteSpace) | (1L << NotHorizontalWhiteSpace) | (1L << NotNewLine) | (1L << CharWithProperty) | (1L << CharWithoutProperty) | (1L << NewLineSequence) | (1L << WhiteSpace) | (1L << NotWhiteSpace) | (1L << VerticalWhiteSpace) | (1L << NotVerticalWhiteSpace) | (1L << WordChar) | (1L << NotWordChar) | (1L << CharacterClassStart) | (1L << Caret) | (1L << Hyphen) | (1L << POSIXNamedSet) | (1L << POSIXNegatedNamedSet) | (1L << QuestionMark) | (1L << Plus) | (1L << Star) | (1L << OpenBrace) | (1L << CloseBrace) | (1L << Comma) | (1L << WordBoundary) | (1L << EndOfSubjectOrLine) | (1L << Pipe) | (1L << OpenParen) | (1L << CloseParen) | (1L << LessThan) | (1L << GreaterThan) | (1L << SingleQuote) | (1L << Underscore) | (1L << Colon) | (1L << Hash) | (1L << Equals) | (1L << Exclamation) | (1L << Ampersand) | (1L << ALC))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)) | (1L << (OtherChar - 64)))) != 0) );
				setState(154);
				match(CharacterClassEnd);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(156);
				match(CharacterClassStart);
				setState(157);
				match(CharacterClassEnd);
				setState(158);
				match(Hyphen);
				setState(160); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(159);
					cc_atom();
					}
					}
					setState(162); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Quoted) | (1L << BlockQuoted) | (1L << BellChar) | (1L << ControlChar) | (1L << EscapeChar) | (1L << FormFeed) | (1L << NewLine) | (1L << CarriageReturn) | (1L << Tab) | (1L << Backslash) | (1L << HexChar) | (1L << Dot) | (1L << DecimalDigit) | (1L << NotDecimalDigit) | (1L << HorizontalWhiteSpace) | (1L << NotHorizontalWhiteSpace) | (1L << NotNewLine) | (1L << CharWithProperty) | (1L << CharWithoutProperty) | (1L << NewLineSequence) | (1L << WhiteSpace) | (1L << NotWhiteSpace) | (1L << VerticalWhiteSpace) | (1L << NotVerticalWhiteSpace) | (1L << WordChar) | (1L << NotWordChar) | (1L << CharacterClassStart) | (1L << Caret) | (1L << Hyphen) | (1L << POSIXNamedSet) | (1L << POSIXNegatedNamedSet) | (1L << QuestionMark) | (1L << Plus) | (1L << Star) | (1L << OpenBrace) | (1L << CloseBrace) | (1L << Comma) | (1L << WordBoundary) | (1L << EndOfSubjectOrLine) | (1L << Pipe) | (1L << OpenParen) | (1L << CloseParen) | (1L << LessThan) | (1L << GreaterThan) | (1L << SingleQuote) | (1L << Underscore) | (1L << Colon) | (1L << Hash) | (1L << Equals) | (1L << Exclamation) | (1L << Ampersand) | (1L << ALC))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)) | (1L << (OtherChar - 64)))) != 0) );
				setState(164);
				match(CharacterClassEnd);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(166);
				match(CharacterClassStart);
				setState(167);
				match(CharacterClassEnd);
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Quoted) | (1L << BlockQuoted) | (1L << BellChar) | (1L << ControlChar) | (1L << EscapeChar) | (1L << FormFeed) | (1L << NewLine) | (1L << CarriageReturn) | (1L << Tab) | (1L << Backslash) | (1L << HexChar) | (1L << Dot) | (1L << DecimalDigit) | (1L << NotDecimalDigit) | (1L << HorizontalWhiteSpace) | (1L << NotHorizontalWhiteSpace) | (1L << NotNewLine) | (1L << CharWithProperty) | (1L << CharWithoutProperty) | (1L << NewLineSequence) | (1L << WhiteSpace) | (1L << NotWhiteSpace) | (1L << VerticalWhiteSpace) | (1L << NotVerticalWhiteSpace) | (1L << WordChar) | (1L << NotWordChar) | (1L << CharacterClassStart) | (1L << Caret) | (1L << Hyphen) | (1L << POSIXNamedSet) | (1L << POSIXNegatedNamedSet) | (1L << QuestionMark) | (1L << Plus) | (1L << Star) | (1L << OpenBrace) | (1L << CloseBrace) | (1L << Comma) | (1L << WordBoundary) | (1L << EndOfSubjectOrLine) | (1L << Pipe) | (1L << OpenParen) | (1L << CloseParen) | (1L << LessThan) | (1L << GreaterThan) | (1L << SingleQuote) | (1L << Underscore) | (1L << Colon) | (1L << Hash) | (1L << Equals) | (1L << Exclamation) | (1L << Ampersand) | (1L << ALC))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)) | (1L << (OtherChar - 64)))) != 0)) {
					{
					{
					setState(168);
					cc_atom();
					}
					}
					setState(173);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(174);
				match(CharacterClassEnd);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(175);
				match(CharacterClassStart);
				setState(177); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(176);
					cc_atom();
					}
					}
					setState(179); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Quoted) | (1L << BlockQuoted) | (1L << BellChar) | (1L << ControlChar) | (1L << EscapeChar) | (1L << FormFeed) | (1L << NewLine) | (1L << CarriageReturn) | (1L << Tab) | (1L << Backslash) | (1L << HexChar) | (1L << Dot) | (1L << DecimalDigit) | (1L << NotDecimalDigit) | (1L << HorizontalWhiteSpace) | (1L << NotHorizontalWhiteSpace) | (1L << NotNewLine) | (1L << CharWithProperty) | (1L << CharWithoutProperty) | (1L << NewLineSequence) | (1L << WhiteSpace) | (1L << NotWhiteSpace) | (1L << VerticalWhiteSpace) | (1L << NotVerticalWhiteSpace) | (1L << WordChar) | (1L << NotWordChar) | (1L << CharacterClassStart) | (1L << Caret) | (1L << Hyphen) | (1L << POSIXNamedSet) | (1L << POSIXNegatedNamedSet) | (1L << QuestionMark) | (1L << Plus) | (1L << Star) | (1L << OpenBrace) | (1L << CloseBrace) | (1L << Comma) | (1L << WordBoundary) | (1L << EndOfSubjectOrLine) | (1L << Pipe) | (1L << OpenParen) | (1L << CloseParen) | (1L << LessThan) | (1L << GreaterThan) | (1L << SingleQuote) | (1L << Underscore) | (1L << Colon) | (1L << Hash) | (1L << Equals) | (1L << Exclamation) | (1L << Ampersand) | (1L << ALC))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)) | (1L << (OtherChar - 64)))) != 0) );
				setState(181);
				match(CharacterClassEnd);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BackreferenceContext extends ParserRuleContext {
		public Backreference_or_octalContext backreference_or_octal() {
			return getRuleContext(Backreference_or_octalContext.class,0);
		}
		public TerminalNode SubroutineOrNamedReferenceStartG() { return getToken(PCREParser.SubroutineOrNamedReferenceStartG, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TerminalNode OpenBrace() { return getToken(PCREParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(PCREParser.CloseBrace, 0); }
		public TerminalNode Hyphen() { return getToken(PCREParser.Hyphen, 0); }
		public TerminalNode NamedReferenceStartK() { return getToken(PCREParser.NamedReferenceStartK, 0); }
		public TerminalNode LessThan() { return getToken(PCREParser.LessThan, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode GreaterThan() { return getToken(PCREParser.GreaterThan, 0); }
		public List<TerminalNode> SingleQuote() { return getTokens(PCREParser.SingleQuote); }
		public TerminalNode SingleQuote(int i) {
			return getToken(PCREParser.SingleQuote, i);
		}
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public TerminalNode PUC() { return getToken(PCREParser.PUC, 0); }
		public TerminalNode Equals() { return getToken(PCREParser.Equals, 0); }
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public BackreferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_backreference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterBackreference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitBackreference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitBackreference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BackreferenceContext backreference() throws RecognitionException {
		BackreferenceContext _localctx = new BackreferenceContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_backreference);
		try {
			setState(226);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(185);
				backreference_or_octal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(186);
				match(SubroutineOrNamedReferenceStartG);
				setState(187);
				number();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(188);
				match(SubroutineOrNamedReferenceStartG);
				setState(189);
				match(OpenBrace);
				setState(190);
				number();
				setState(191);
				match(CloseBrace);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(193);
				match(SubroutineOrNamedReferenceStartG);
				setState(194);
				match(OpenBrace);
				setState(195);
				match(Hyphen);
				setState(196);
				number();
				setState(197);
				match(CloseBrace);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(199);
				match(NamedReferenceStartK);
				setState(200);
				match(LessThan);
				setState(201);
				name();
				setState(202);
				match(GreaterThan);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(204);
				match(NamedReferenceStartK);
				setState(205);
				match(SingleQuote);
				setState(206);
				name();
				setState(207);
				match(SingleQuote);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(209);
				match(SubroutineOrNamedReferenceStartG);
				setState(210);
				match(OpenBrace);
				setState(211);
				name();
				setState(212);
				match(CloseBrace);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(214);
				match(NamedReferenceStartK);
				setState(215);
				match(OpenBrace);
				setState(216);
				name();
				setState(217);
				match(CloseBrace);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(219);
				match(OpenParen);
				setState(220);
				match(QuestionMark);
				setState(221);
				match(PUC);
				setState(222);
				match(Equals);
				setState(223);
				name();
				setState(224);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Backreference_or_octalContext extends ParserRuleContext {
		public Octal_charContext octal_char() {
			return getRuleContext(Octal_charContext.class,0);
		}
		public TerminalNode Backslash() { return getToken(PCREParser.Backslash, 0); }
		public DigitContext digit() {
			return getRuleContext(DigitContext.class,0);
		}
		public Backreference_or_octalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_backreference_or_octal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterBackreference_or_octal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitBackreference_or_octal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitBackreference_or_octal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Backreference_or_octalContext backreference_or_octal() throws RecognitionException {
		Backreference_or_octalContext _localctx = new Backreference_or_octalContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_backreference_or_octal);
		try {
			setState(231);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				octal_char();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(229);
				match(Backslash);
				setState(230);
				digit();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaptureContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public TerminalNode LessThan() { return getToken(PCREParser.LessThan, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode GreaterThan() { return getToken(PCREParser.GreaterThan, 0); }
		public AlternationContext alternation() {
			return getRuleContext(AlternationContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public List<TerminalNode> SingleQuote() { return getTokens(PCREParser.SingleQuote); }
		public TerminalNode SingleQuote(int i) {
			return getToken(PCREParser.SingleQuote, i);
		}
		public TerminalNode PUC() { return getToken(PCREParser.PUC, 0); }
		public CaptureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_capture; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterCapture(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitCapture(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitCapture(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaptureContext capture() throws RecognitionException {
		CaptureContext _localctx = new CaptureContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_capture);
		try {
			setState(262);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(233);
				match(OpenParen);
				setState(234);
				match(QuestionMark);
				setState(235);
				match(LessThan);
				setState(236);
				name();
				setState(237);
				match(GreaterThan);
				setState(238);
				alternation();
				setState(239);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(241);
				match(OpenParen);
				setState(242);
				match(QuestionMark);
				setState(243);
				match(SingleQuote);
				setState(244);
				name();
				setState(245);
				match(SingleQuote);
				setState(246);
				alternation();
				setState(247);
				match(CloseParen);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(249);
				match(OpenParen);
				setState(250);
				match(QuestionMark);
				setState(251);
				match(PUC);
				setState(252);
				match(LessThan);
				setState(253);
				name();
				setState(254);
				match(GreaterThan);
				setState(255);
				alternation();
				setState(256);
				match(CloseParen);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(258);
				match(OpenParen);
				setState(259);
				alternation();
				setState(260);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Non_captureContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public TerminalNode Colon() { return getToken(PCREParser.Colon, 0); }
		public AlternationContext alternation() {
			return getRuleContext(AlternationContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public TerminalNode Pipe() { return getToken(PCREParser.Pipe, 0); }
		public TerminalNode GreaterThan() { return getToken(PCREParser.GreaterThan, 0); }
		public Option_flagsContext option_flags() {
			return getRuleContext(Option_flagsContext.class,0);
		}
		public Non_captureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_non_capture; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterNon_capture(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitNon_capture(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitNon_capture(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Non_captureContext non_capture() throws RecognitionException {
		Non_captureContext _localctx = new Non_captureContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_non_capture);
		try {
			setState(289);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(264);
				match(OpenParen);
				setState(265);
				match(QuestionMark);
				setState(266);
				match(Colon);
				setState(267);
				alternation();
				setState(268);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				match(OpenParen);
				setState(271);
				match(QuestionMark);
				setState(272);
				match(Pipe);
				setState(273);
				alternation();
				setState(274);
				match(CloseParen);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(276);
				match(OpenParen);
				setState(277);
				match(QuestionMark);
				setState(278);
				match(GreaterThan);
				setState(279);
				alternation();
				setState(280);
				match(CloseParen);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(282);
				match(OpenParen);
				setState(283);
				match(QuestionMark);
				setState(284);
				option_flags();
				setState(285);
				match(Colon);
				setState(286);
				alternation();
				setState(287);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public TerminalNode Hash() { return getToken(PCREParser.Hash, 0); }
		public Non_close_parensContext non_close_parens() {
			return getRuleContext(Non_close_parensContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			match(OpenParen);
			setState(292);
			match(QuestionMark);
			setState(293);
			match(Hash);
			setState(294);
			non_close_parens();
			setState(295);
			match(CloseParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public List<Option_flagsContext> option_flags() {
			return getRuleContexts(Option_flagsContext.class);
		}
		public Option_flagsContext option_flags(int i) {
			return getRuleContext(Option_flagsContext.class,i);
		}
		public TerminalNode Hyphen() { return getToken(PCREParser.Hyphen, 0); }
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public TerminalNode Star() { return getToken(PCREParser.Star, 0); }
		public TerminalNode NUC() { return getToken(PCREParser.NUC, 0); }
		public List<TerminalNode> OUC() { return getTokens(PCREParser.OUC); }
		public TerminalNode OUC(int i) {
			return getToken(PCREParser.OUC, i);
		}
		public List<TerminalNode> Underscore() { return getTokens(PCREParser.Underscore); }
		public TerminalNode Underscore(int i) {
			return getToken(PCREParser.Underscore, i);
		}
		public TerminalNode SUC() { return getToken(PCREParser.SUC, 0); }
		public List<TerminalNode> TUC() { return getTokens(PCREParser.TUC); }
		public TerminalNode TUC(int i) {
			return getToken(PCREParser.TUC, i);
		}
		public TerminalNode AUC() { return getToken(PCREParser.AUC, 0); }
		public TerminalNode RUC() { return getToken(PCREParser.RUC, 0); }
		public TerminalNode PUC() { return getToken(PCREParser.PUC, 0); }
		public TerminalNode UUC() { return getToken(PCREParser.UUC, 0); }
		public TerminalNode FUC() { return getToken(PCREParser.FUC, 0); }
		public TerminalNode D8() { return getToken(PCREParser.D8, 0); }
		public TerminalNode D1() { return getToken(PCREParser.D1, 0); }
		public TerminalNode D6() { return getToken(PCREParser.D6, 0); }
		public TerminalNode CUC() { return getToken(PCREParser.CUC, 0); }
		public OptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionContext option() throws RecognitionException {
		OptionContext _localctx = new OptionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_option);
		try {
			setState(351);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(297);
				match(OpenParen);
				setState(298);
				match(QuestionMark);
				setState(299);
				option_flags();
				setState(300);
				match(Hyphen);
				setState(301);
				option_flags();
				setState(302);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(304);
				match(OpenParen);
				setState(305);
				match(QuestionMark);
				setState(306);
				option_flags();
				setState(307);
				match(CloseParen);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(309);
				match(OpenParen);
				setState(310);
				match(QuestionMark);
				setState(311);
				match(Hyphen);
				setState(312);
				option_flags();
				setState(313);
				match(CloseParen);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(315);
				match(OpenParen);
				setState(316);
				match(Star);
				setState(317);
				match(NUC);
				setState(318);
				match(OUC);
				setState(319);
				match(Underscore);
				setState(320);
				match(SUC);
				setState(321);
				match(TUC);
				setState(322);
				match(AUC);
				setState(323);
				match(RUC);
				setState(324);
				match(TUC);
				setState(325);
				match(Underscore);
				setState(326);
				match(OUC);
				setState(327);
				match(PUC);
				setState(328);
				match(TUC);
				setState(329);
				match(CloseParen);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(330);
				match(OpenParen);
				setState(331);
				match(Star);
				setState(332);
				match(UUC);
				setState(333);
				match(TUC);
				setState(334);
				match(FUC);
				setState(335);
				match(D8);
				setState(336);
				match(CloseParen);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(337);
				match(OpenParen);
				setState(338);
				match(Star);
				setState(339);
				match(UUC);
				setState(340);
				match(TUC);
				setState(341);
				match(FUC);
				setState(342);
				match(D1);
				setState(343);
				match(D6);
				setState(344);
				match(CloseParen);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(345);
				match(OpenParen);
				setState(346);
				match(Star);
				setState(347);
				match(UUC);
				setState(348);
				match(CUC);
				setState(349);
				match(PUC);
				setState(350);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Option_flagsContext extends ParserRuleContext {
		public List<Option_flagContext> option_flag() {
			return getRuleContexts(Option_flagContext.class);
		}
		public Option_flagContext option_flag(int i) {
			return getRuleContext(Option_flagContext.class,i);
		}
		public Option_flagsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option_flags; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterOption_flags(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitOption_flags(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitOption_flags(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Option_flagsContext option_flags() throws RecognitionException {
		Option_flagsContext _localctx = new Option_flagsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_option_flags);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(354); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(353);
				option_flag();
				}
				}
				setState(356); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (ILC - 71)) | (1L << (MLC - 71)) | (1L << (SLC - 71)) | (1L << (XLC - 71)) | (1L << (JUC - 71)) | (1L << (UUC - 71)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Option_flagContext extends ParserRuleContext {
		public TerminalNode ILC() { return getToken(PCREParser.ILC, 0); }
		public TerminalNode JUC() { return getToken(PCREParser.JUC, 0); }
		public TerminalNode MLC() { return getToken(PCREParser.MLC, 0); }
		public TerminalNode SLC() { return getToken(PCREParser.SLC, 0); }
		public TerminalNode UUC() { return getToken(PCREParser.UUC, 0); }
		public TerminalNode XLC() { return getToken(PCREParser.XLC, 0); }
		public Option_flagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option_flag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterOption_flag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitOption_flag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitOption_flag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Option_flagContext option_flag() throws RecognitionException {
		Option_flagContext _localctx = new Option_flagContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_option_flag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			_la = _input.LA(1);
			if ( !(((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (ILC - 71)) | (1L << (MLC - 71)) | (1L << (SLC - 71)) | (1L << (XLC - 71)) | (1L << (JUC - 71)) | (1L << (UUC - 71)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Look_aroundContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public TerminalNode Equals() { return getToken(PCREParser.Equals, 0); }
		public AlternationContext alternation() {
			return getRuleContext(AlternationContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public TerminalNode Exclamation() { return getToken(PCREParser.Exclamation, 0); }
		public TerminalNode LessThan() { return getToken(PCREParser.LessThan, 0); }
		public Look_aroundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_look_around; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterLook_around(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitLook_around(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitLook_around(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Look_aroundContext look_around() throws RecognitionException {
		Look_aroundContext _localctx = new Look_aroundContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_look_around);
		try {
			setState(386);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(360);
				match(OpenParen);
				setState(361);
				match(QuestionMark);
				setState(362);
				match(Equals);
				setState(363);
				alternation();
				setState(364);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(366);
				match(OpenParen);
				setState(367);
				match(QuestionMark);
				setState(368);
				match(Exclamation);
				setState(369);
				alternation();
				setState(370);
				match(CloseParen);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(372);
				match(OpenParen);
				setState(373);
				match(QuestionMark);
				setState(374);
				match(LessThan);
				setState(375);
				match(Equals);
				setState(376);
				alternation();
				setState(377);
				match(CloseParen);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(379);
				match(OpenParen);
				setState(380);
				match(QuestionMark);
				setState(381);
				match(LessThan);
				setState(382);
				match(Exclamation);
				setState(383);
				alternation();
				setState(384);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Subroutine_referenceContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public TerminalNode RUC() { return getToken(PCREParser.RUC, 0); }
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TerminalNode Plus() { return getToken(PCREParser.Plus, 0); }
		public TerminalNode Hyphen() { return getToken(PCREParser.Hyphen, 0); }
		public TerminalNode Ampersand() { return getToken(PCREParser.Ampersand, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode PUC() { return getToken(PCREParser.PUC, 0); }
		public TerminalNode GreaterThan() { return getToken(PCREParser.GreaterThan, 0); }
		public TerminalNode SubroutineOrNamedReferenceStartG() { return getToken(PCREParser.SubroutineOrNamedReferenceStartG, 0); }
		public TerminalNode LessThan() { return getToken(PCREParser.LessThan, 0); }
		public List<TerminalNode> SingleQuote() { return getTokens(PCREParser.SingleQuote); }
		public TerminalNode SingleQuote(int i) {
			return getToken(PCREParser.SingleQuote, i);
		}
		public Subroutine_referenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subroutine_reference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterSubroutine_reference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitSubroutine_reference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitSubroutine_reference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Subroutine_referenceContext subroutine_reference() throws RecognitionException {
		Subroutine_referenceContext _localctx = new Subroutine_referenceContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_subroutine_reference);
		try {
			setState(466);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(388);
				match(OpenParen);
				setState(389);
				match(QuestionMark);
				setState(390);
				match(RUC);
				setState(391);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(392);
				match(OpenParen);
				setState(393);
				match(QuestionMark);
				setState(394);
				number();
				setState(395);
				match(CloseParen);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(397);
				match(OpenParen);
				setState(398);
				match(QuestionMark);
				setState(399);
				match(Plus);
				setState(400);
				number();
				setState(401);
				match(CloseParen);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(403);
				match(OpenParen);
				setState(404);
				match(QuestionMark);
				setState(405);
				match(Hyphen);
				setState(406);
				number();
				setState(407);
				match(CloseParen);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(409);
				match(OpenParen);
				setState(410);
				match(QuestionMark);
				setState(411);
				match(Ampersand);
				setState(412);
				name();
				setState(413);
				match(CloseParen);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(415);
				match(OpenParen);
				setState(416);
				match(QuestionMark);
				setState(417);
				match(PUC);
				setState(418);
				match(GreaterThan);
				setState(419);
				name();
				setState(420);
				match(CloseParen);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(422);
				match(SubroutineOrNamedReferenceStartG);
				setState(423);
				match(LessThan);
				setState(424);
				name();
				setState(425);
				match(GreaterThan);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(427);
				match(SubroutineOrNamedReferenceStartG);
				setState(428);
				match(SingleQuote);
				setState(429);
				name();
				setState(430);
				match(SingleQuote);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(432);
				match(SubroutineOrNamedReferenceStartG);
				setState(433);
				match(LessThan);
				setState(434);
				number();
				setState(435);
				match(GreaterThan);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(437);
				match(SubroutineOrNamedReferenceStartG);
				setState(438);
				match(SingleQuote);
				setState(439);
				number();
				setState(440);
				match(SingleQuote);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(442);
				match(SubroutineOrNamedReferenceStartG);
				setState(443);
				match(LessThan);
				setState(444);
				match(Plus);
				setState(445);
				number();
				setState(446);
				match(GreaterThan);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(448);
				match(SubroutineOrNamedReferenceStartG);
				setState(449);
				match(SingleQuote);
				setState(450);
				match(Plus);
				setState(451);
				number();
				setState(452);
				match(SingleQuote);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(454);
				match(SubroutineOrNamedReferenceStartG);
				setState(455);
				match(LessThan);
				setState(456);
				match(Hyphen);
				setState(457);
				number();
				setState(458);
				match(GreaterThan);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(460);
				match(SubroutineOrNamedReferenceStartG);
				setState(461);
				match(SingleQuote);
				setState(462);
				match(Hyphen);
				setState(463);
				number();
				setState(464);
				match(SingleQuote);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalContext extends ParserRuleContext {
		public List<TerminalNode> OpenParen() { return getTokens(PCREParser.OpenParen); }
		public TerminalNode OpenParen(int i) {
			return getToken(PCREParser.OpenParen, i);
		}
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public List<TerminalNode> CloseParen() { return getTokens(PCREParser.CloseParen); }
		public TerminalNode CloseParen(int i) {
			return getToken(PCREParser.CloseParen, i);
		}
		public List<AlternationContext> alternation() {
			return getRuleContexts(AlternationContext.class);
		}
		public AlternationContext alternation(int i) {
			return getRuleContext(AlternationContext.class,i);
		}
		public TerminalNode Pipe() { return getToken(PCREParser.Pipe, 0); }
		public TerminalNode Plus() { return getToken(PCREParser.Plus, 0); }
		public TerminalNode Hyphen() { return getToken(PCREParser.Hyphen, 0); }
		public TerminalNode LessThan() { return getToken(PCREParser.LessThan, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode GreaterThan() { return getToken(PCREParser.GreaterThan, 0); }
		public List<TerminalNode> SingleQuote() { return getTokens(PCREParser.SingleQuote); }
		public TerminalNode SingleQuote(int i) {
			return getToken(PCREParser.SingleQuote, i);
		}
		public TerminalNode RUC() { return getToken(PCREParser.RUC, 0); }
		public TerminalNode Ampersand() { return getToken(PCREParser.Ampersand, 0); }
		public TerminalNode DUC() { return getToken(PCREParser.DUC, 0); }
		public List<TerminalNode> EUC() { return getTokens(PCREParser.EUC); }
		public TerminalNode EUC(int i) {
			return getToken(PCREParser.EUC, i);
		}
		public TerminalNode FUC() { return getToken(PCREParser.FUC, 0); }
		public TerminalNode IUC() { return getToken(PCREParser.IUC, 0); }
		public TerminalNode NUC() { return getToken(PCREParser.NUC, 0); }
		public TerminalNode ALC() { return getToken(PCREParser.ALC, 0); }
		public List<TerminalNode> SLC() { return getTokens(PCREParser.SLC); }
		public TerminalNode SLC(int i) {
			return getToken(PCREParser.SLC, i);
		}
		public TerminalNode ELC() { return getToken(PCREParser.ELC, 0); }
		public TerminalNode RLC() { return getToken(PCREParser.RLC, 0); }
		public TerminalNode TLC() { return getToken(PCREParser.TLC, 0); }
		public ConditionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditional; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitConditional(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitConditional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalContext conditional() throws RecognitionException {
		ConditionalContext _localctx = new ConditionalContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_conditional);
		int _la;
		try {
			setState(619);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(468);
				match(OpenParen);
				setState(469);
				match(QuestionMark);
				setState(470);
				match(OpenParen);
				setState(471);
				number();
				setState(472);
				match(CloseParen);
				setState(473);
				alternation();
				setState(476);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(474);
					match(Pipe);
					setState(475);
					alternation();
					}
				}

				setState(478);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(480);
				match(OpenParen);
				setState(481);
				match(QuestionMark);
				setState(482);
				match(OpenParen);
				setState(483);
				match(Plus);
				setState(484);
				number();
				setState(485);
				match(CloseParen);
				setState(486);
				alternation();
				setState(489);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(487);
					match(Pipe);
					setState(488);
					alternation();
					}
				}

				setState(491);
				match(CloseParen);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(493);
				match(OpenParen);
				setState(494);
				match(QuestionMark);
				setState(495);
				match(OpenParen);
				setState(496);
				match(Hyphen);
				setState(497);
				number();
				setState(498);
				match(CloseParen);
				setState(499);
				alternation();
				setState(502);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(500);
					match(Pipe);
					setState(501);
					alternation();
					}
				}

				setState(504);
				match(CloseParen);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(506);
				match(OpenParen);
				setState(507);
				match(QuestionMark);
				setState(508);
				match(OpenParen);
				setState(509);
				match(LessThan);
				setState(510);
				name();
				setState(511);
				match(GreaterThan);
				setState(512);
				match(CloseParen);
				setState(513);
				alternation();
				setState(516);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(514);
					match(Pipe);
					setState(515);
					alternation();
					}
				}

				setState(518);
				match(CloseParen);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(520);
				match(OpenParen);
				setState(521);
				match(QuestionMark);
				setState(522);
				match(OpenParen);
				setState(523);
				match(SingleQuote);
				setState(524);
				name();
				setState(525);
				match(SingleQuote);
				setState(526);
				match(CloseParen);
				setState(527);
				alternation();
				setState(530);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(528);
					match(Pipe);
					setState(529);
					alternation();
					}
				}

				setState(532);
				match(CloseParen);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(534);
				match(OpenParen);
				setState(535);
				match(QuestionMark);
				setState(536);
				match(OpenParen);
				setState(537);
				match(RUC);
				setState(538);
				number();
				setState(539);
				match(CloseParen);
				setState(540);
				alternation();
				setState(543);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(541);
					match(Pipe);
					setState(542);
					alternation();
					}
				}

				setState(545);
				match(CloseParen);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(547);
				match(OpenParen);
				setState(548);
				match(QuestionMark);
				setState(549);
				match(OpenParen);
				setState(550);
				match(RUC);
				setState(551);
				match(CloseParen);
				setState(552);
				alternation();
				setState(555);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(553);
					match(Pipe);
					setState(554);
					alternation();
					}
				}

				setState(557);
				match(CloseParen);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(559);
				match(OpenParen);
				setState(560);
				match(QuestionMark);
				setState(561);
				match(OpenParen);
				setState(562);
				match(RUC);
				setState(563);
				match(Ampersand);
				setState(564);
				name();
				setState(565);
				match(CloseParen);
				setState(566);
				alternation();
				setState(569);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(567);
					match(Pipe);
					setState(568);
					alternation();
					}
				}

				setState(571);
				match(CloseParen);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(573);
				match(OpenParen);
				setState(574);
				match(QuestionMark);
				setState(575);
				match(OpenParen);
				setState(576);
				match(DUC);
				setState(577);
				match(EUC);
				setState(578);
				match(FUC);
				setState(579);
				match(IUC);
				setState(580);
				match(NUC);
				setState(581);
				match(EUC);
				setState(582);
				match(CloseParen);
				setState(583);
				alternation();
				setState(586);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(584);
					match(Pipe);
					setState(585);
					alternation();
					}
				}

				setState(588);
				match(CloseParen);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(590);
				match(OpenParen);
				setState(591);
				match(QuestionMark);
				setState(592);
				match(OpenParen);
				setState(593);
				match(ALC);
				setState(594);
				match(SLC);
				setState(595);
				match(SLC);
				setState(596);
				match(ELC);
				setState(597);
				match(RLC);
				setState(598);
				match(TLC);
				setState(599);
				match(CloseParen);
				setState(600);
				alternation();
				setState(603);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(601);
					match(Pipe);
					setState(602);
					alternation();
					}
				}

				setState(605);
				match(CloseParen);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(607);
				match(OpenParen);
				setState(608);
				match(QuestionMark);
				setState(609);
				match(OpenParen);
				setState(610);
				name();
				setState(611);
				match(CloseParen);
				setState(612);
				alternation();
				setState(615);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Pipe) {
					{
					setState(613);
					match(Pipe);
					setState(614);
					alternation();
					}
				}

				setState(617);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Backtrack_controlContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode Star() { return getToken(PCREParser.Star, 0); }
		public List<TerminalNode> AUC() { return getTokens(PCREParser.AUC); }
		public TerminalNode AUC(int i) {
			return getToken(PCREParser.AUC, i);
		}
		public List<TerminalNode> CUC() { return getTokens(PCREParser.CUC); }
		public TerminalNode CUC(int i) {
			return getToken(PCREParser.CUC, i);
		}
		public List<TerminalNode> EUC() { return getTokens(PCREParser.EUC); }
		public TerminalNode EUC(int i) {
			return getToken(PCREParser.EUC, i);
		}
		public TerminalNode PUC() { return getToken(PCREParser.PUC, 0); }
		public TerminalNode TUC() { return getToken(PCREParser.TUC, 0); }
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public TerminalNode FUC() { return getToken(PCREParser.FUC, 0); }
		public TerminalNode IUC() { return getToken(PCREParser.IUC, 0); }
		public TerminalNode LUC() { return getToken(PCREParser.LUC, 0); }
		public TerminalNode Colon() { return getToken(PCREParser.Colon, 0); }
		public List<TerminalNode> NUC() { return getTokens(PCREParser.NUC); }
		public TerminalNode NUC(int i) {
			return getToken(PCREParser.NUC, i);
		}
		public List<TerminalNode> MUC() { return getTokens(PCREParser.MUC); }
		public TerminalNode MUC(int i) {
			return getToken(PCREParser.MUC, i);
		}
		public TerminalNode RUC() { return getToken(PCREParser.RUC, 0); }
		public TerminalNode KUC() { return getToken(PCREParser.KUC, 0); }
		public TerminalNode OUC() { return getToken(PCREParser.OUC, 0); }
		public TerminalNode UUC() { return getToken(PCREParser.UUC, 0); }
		public TerminalNode SUC() { return getToken(PCREParser.SUC, 0); }
		public TerminalNode HUC() { return getToken(PCREParser.HUC, 0); }
		public Backtrack_controlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_backtrack_control; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterBacktrack_control(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitBacktrack_control(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitBacktrack_control(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Backtrack_controlContext backtrack_control() throws RecognitionException {
		Backtrack_controlContext _localctx = new Backtrack_controlContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_backtrack_control);
		int _la;
		try {
			setState(721);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(621);
				match(OpenParen);
				setState(622);
				match(Star);
				setState(623);
				match(AUC);
				setState(624);
				match(CUC);
				setState(625);
				match(CUC);
				setState(626);
				match(EUC);
				setState(627);
				match(PUC);
				setState(628);
				match(TUC);
				setState(629);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(630);
				match(OpenParen);
				setState(631);
				match(Star);
				setState(632);
				match(FUC);
				setState(636);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AUC) {
					{
					setState(633);
					match(AUC);
					setState(634);
					match(IUC);
					setState(635);
					match(LUC);
					}
				}

				setState(638);
				match(CloseParen);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(639);
				match(OpenParen);
				setState(640);
				match(Star);
				setState(645);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MUC) {
					{
					setState(641);
					match(MUC);
					setState(642);
					match(AUC);
					setState(643);
					match(RUC);
					setState(644);
					match(KUC);
					}
				}

				setState(647);
				match(Colon);
				setState(648);
				match(NUC);
				setState(649);
				match(AUC);
				setState(650);
				match(MUC);
				setState(651);
				match(EUC);
				setState(652);
				match(CloseParen);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(653);
				match(OpenParen);
				setState(654);
				match(Star);
				setState(655);
				match(CUC);
				setState(656);
				match(OUC);
				setState(657);
				match(MUC);
				setState(658);
				match(MUC);
				setState(659);
				match(IUC);
				setState(660);
				match(TUC);
				setState(661);
				match(CloseParen);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(662);
				match(OpenParen);
				setState(663);
				match(Star);
				setState(664);
				match(PUC);
				setState(665);
				match(RUC);
				setState(666);
				match(UUC);
				setState(667);
				match(NUC);
				setState(668);
				match(EUC);
				setState(669);
				match(CloseParen);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(670);
				match(OpenParen);
				setState(671);
				match(Star);
				setState(672);
				match(PUC);
				setState(673);
				match(RUC);
				setState(674);
				match(UUC);
				setState(675);
				match(NUC);
				setState(676);
				match(EUC);
				setState(677);
				match(Colon);
				setState(678);
				match(NUC);
				setState(679);
				match(AUC);
				setState(680);
				match(MUC);
				setState(681);
				match(EUC);
				setState(682);
				match(CloseParen);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(683);
				match(OpenParen);
				setState(684);
				match(Star);
				setState(685);
				match(SUC);
				setState(686);
				match(KUC);
				setState(687);
				match(IUC);
				setState(688);
				match(PUC);
				setState(689);
				match(CloseParen);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(690);
				match(OpenParen);
				setState(691);
				match(Star);
				setState(692);
				match(SUC);
				setState(693);
				match(KUC);
				setState(694);
				match(IUC);
				setState(695);
				match(PUC);
				setState(696);
				match(Colon);
				setState(697);
				match(NUC);
				setState(698);
				match(AUC);
				setState(699);
				match(MUC);
				setState(700);
				match(EUC);
				setState(701);
				match(CloseParen);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(702);
				match(OpenParen);
				setState(703);
				match(Star);
				setState(704);
				match(TUC);
				setState(705);
				match(HUC);
				setState(706);
				match(EUC);
				setState(707);
				match(NUC);
				setState(708);
				match(CloseParen);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(709);
				match(OpenParen);
				setState(710);
				match(Star);
				setState(711);
				match(TUC);
				setState(712);
				match(HUC);
				setState(713);
				match(EUC);
				setState(714);
				match(NUC);
				setState(715);
				match(Colon);
				setState(716);
				match(NUC);
				setState(717);
				match(AUC);
				setState(718);
				match(MUC);
				setState(719);
				match(EUC);
				setState(720);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Newline_conventionContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode Star() { return getToken(PCREParser.Star, 0); }
		public TerminalNode CUC() { return getToken(PCREParser.CUC, 0); }
		public List<TerminalNode> RUC() { return getTokens(PCREParser.RUC); }
		public TerminalNode RUC(int i) {
			return getToken(PCREParser.RUC, i);
		}
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public TerminalNode LUC() { return getToken(PCREParser.LUC, 0); }
		public TerminalNode FUC() { return getToken(PCREParser.FUC, 0); }
		public TerminalNode AUC() { return getToken(PCREParser.AUC, 0); }
		public TerminalNode NUC() { return getToken(PCREParser.NUC, 0); }
		public TerminalNode YUC() { return getToken(PCREParser.YUC, 0); }
		public TerminalNode BUC() { return getToken(PCREParser.BUC, 0); }
		public TerminalNode SUC() { return getToken(PCREParser.SUC, 0); }
		public TerminalNode Underscore() { return getToken(PCREParser.Underscore, 0); }
		public TerminalNode UUC() { return getToken(PCREParser.UUC, 0); }
		public TerminalNode IUC() { return getToken(PCREParser.IUC, 0); }
		public TerminalNode OUC() { return getToken(PCREParser.OUC, 0); }
		public TerminalNode DUC() { return getToken(PCREParser.DUC, 0); }
		public TerminalNode EUC() { return getToken(PCREParser.EUC, 0); }
		public Newline_conventionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newline_convention; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterNewline_convention(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitNewline_convention(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitNewline_convention(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Newline_conventionContext newline_convention() throws RecognitionException {
		Newline_conventionContext _localctx = new Newline_conventionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_newline_convention);
		try {
			setState(784);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(723);
				match(OpenParen);
				setState(724);
				match(Star);
				setState(725);
				match(CUC);
				setState(726);
				match(RUC);
				setState(727);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(728);
				match(OpenParen);
				setState(729);
				match(Star);
				setState(730);
				match(LUC);
				setState(731);
				match(FUC);
				setState(732);
				match(CloseParen);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(733);
				match(OpenParen);
				setState(734);
				match(Star);
				setState(735);
				match(CUC);
				setState(736);
				match(RUC);
				setState(737);
				match(LUC);
				setState(738);
				match(FUC);
				setState(739);
				match(CloseParen);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(740);
				match(OpenParen);
				setState(741);
				match(Star);
				setState(742);
				match(AUC);
				setState(743);
				match(NUC);
				setState(744);
				match(YUC);
				setState(745);
				match(CUC);
				setState(746);
				match(RUC);
				setState(747);
				match(LUC);
				setState(748);
				match(FUC);
				setState(749);
				match(CloseParen);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(750);
				match(OpenParen);
				setState(751);
				match(Star);
				setState(752);
				match(AUC);
				setState(753);
				match(NUC);
				setState(754);
				match(YUC);
				setState(755);
				match(CloseParen);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(756);
				match(OpenParen);
				setState(757);
				match(Star);
				setState(758);
				match(BUC);
				setState(759);
				match(SUC);
				setState(760);
				match(RUC);
				setState(761);
				match(Underscore);
				setState(762);
				match(AUC);
				setState(763);
				match(NUC);
				setState(764);
				match(YUC);
				setState(765);
				match(CUC);
				setState(766);
				match(RUC);
				setState(767);
				match(LUC);
				setState(768);
				match(FUC);
				setState(769);
				match(CloseParen);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(770);
				match(OpenParen);
				setState(771);
				match(Star);
				setState(772);
				match(BUC);
				setState(773);
				match(SUC);
				setState(774);
				match(RUC);
				setState(775);
				match(Underscore);
				setState(776);
				match(UUC);
				setState(777);
				match(NUC);
				setState(778);
				match(IUC);
				setState(779);
				match(CUC);
				setState(780);
				match(OUC);
				setState(781);
				match(DUC);
				setState(782);
				match(EUC);
				setState(783);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CalloutContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public TerminalNode CUC() { return getToken(PCREParser.CUC, 0); }
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public CalloutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callout; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterCallout(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitCallout(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitCallout(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CalloutContext callout() throws RecognitionException {
		CalloutContext _localctx = new CalloutContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_callout);
		try {
			setState(796);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(786);
				match(OpenParen);
				setState(787);
				match(QuestionMark);
				setState(788);
				match(CUC);
				setState(789);
				match(CloseParen);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(790);
				match(OpenParen);
				setState(791);
				match(QuestionMark);
				setState(792);
				match(CUC);
				setState(793);
				number();
				setState(794);
				match(CloseParen);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public Subroutine_referenceContext subroutine_reference() {
			return getRuleContext(Subroutine_referenceContext.class,0);
		}
		public Shared_atomContext shared_atom() {
			return getRuleContext(Shared_atomContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public Character_classContext character_class() {
			return getRuleContext(Character_classContext.class,0);
		}
		public CaptureContext capture() {
			return getRuleContext(CaptureContext.class,0);
		}
		public Non_captureContext non_capture() {
			return getRuleContext(Non_captureContext.class,0);
		}
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public OptionContext option() {
			return getRuleContext(OptionContext.class,0);
		}
		public Look_aroundContext look_around() {
			return getRuleContext(Look_aroundContext.class,0);
		}
		public BackreferenceContext backreference() {
			return getRuleContext(BackreferenceContext.class,0);
		}
		public ConditionalContext conditional() {
			return getRuleContext(ConditionalContext.class,0);
		}
		public Backtrack_controlContext backtrack_control() {
			return getRuleContext(Backtrack_controlContext.class,0);
		}
		public Newline_conventionContext newline_convention() {
			return getRuleContext(Newline_conventionContext.class,0);
		}
		public CalloutContext callout() {
			return getRuleContext(CalloutContext.class,0);
		}
		public TerminalNode Dot() { return getToken(PCREParser.Dot, 0); }
		public TerminalNode Caret() { return getToken(PCREParser.Caret, 0); }
		public TerminalNode StartOfSubject() { return getToken(PCREParser.StartOfSubject, 0); }
		public TerminalNode WordBoundary() { return getToken(PCREParser.WordBoundary, 0); }
		public TerminalNode NonWordBoundary() { return getToken(PCREParser.NonWordBoundary, 0); }
		public TerminalNode EndOfSubjectOrLine() { return getToken(PCREParser.EndOfSubjectOrLine, 0); }
		public TerminalNode EndOfSubjectOrLineEndOfSubject() { return getToken(PCREParser.EndOfSubjectOrLineEndOfSubject, 0); }
		public TerminalNode EndOfSubject() { return getToken(PCREParser.EndOfSubject, 0); }
		public TerminalNode PreviousMatchInSubject() { return getToken(PCREParser.PreviousMatchInSubject, 0); }
		public TerminalNode ResetStartMatch() { return getToken(PCREParser.ResetStartMatch, 0); }
		public TerminalNode OneDataUnit() { return getToken(PCREParser.OneDataUnit, 0); }
		public TerminalNode ExtendedUnicodeChar() { return getToken(PCREParser.ExtendedUnicodeChar, 0); }
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_atom);
		try {
			setState(824);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(798);
				subroutine_reference();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(799);
				shared_atom();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(800);
				literal();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(801);
				character_class();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(802);
				capture();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(803);
				non_capture();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(804);
				comment();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(805);
				option();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(806);
				look_around();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(807);
				backreference();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(808);
				conditional();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(809);
				backtrack_control();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(810);
				newline_convention();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(811);
				callout();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(812);
				match(Dot);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(813);
				match(Caret);
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(814);
				match(StartOfSubject);
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(815);
				match(WordBoundary);
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(816);
				match(NonWordBoundary);
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(817);
				match(EndOfSubjectOrLine);
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(818);
				match(EndOfSubjectOrLineEndOfSubject);
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(819);
				match(EndOfSubject);
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 23);
				{
				setState(820);
				match(PreviousMatchInSubject);
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 24);
				{
				setState(821);
				match(ResetStartMatch);
				}
				break;
			case 25:
				enterOuterAlt(_localctx, 25);
				{
				setState(822);
				match(OneDataUnit);
				}
				break;
			case 26:
				enterOuterAlt(_localctx, 26);
				{
				setState(823);
				match(ExtendedUnicodeChar);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Cc_atomContext extends ParserRuleContext {
		public List<Cc_literalContext> cc_literal() {
			return getRuleContexts(Cc_literalContext.class);
		}
		public Cc_literalContext cc_literal(int i) {
			return getRuleContext(Cc_literalContext.class,i);
		}
		public TerminalNode Hyphen() { return getToken(PCREParser.Hyphen, 0); }
		public Shared_atomContext shared_atom() {
			return getRuleContext(Shared_atomContext.class,0);
		}
		public Backreference_or_octalContext backreference_or_octal() {
			return getRuleContext(Backreference_or_octalContext.class,0);
		}
		public Cc_atomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cc_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterCc_atom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitCc_atom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitCc_atom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Cc_atomContext cc_atom() throws RecognitionException {
		Cc_atomContext _localctx = new Cc_atomContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_cc_atom);
		try {
			setState(833);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(826);
				cc_literal();
				setState(827);
				match(Hyphen);
				setState(828);
				cc_literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(830);
				shared_atom();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(831);
				cc_literal();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(832);
				backreference_or_octal();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Shared_atomContext extends ParserRuleContext {
		public TerminalNode POSIXNamedSet() { return getToken(PCREParser.POSIXNamedSet, 0); }
		public TerminalNode POSIXNegatedNamedSet() { return getToken(PCREParser.POSIXNegatedNamedSet, 0); }
		public TerminalNode ControlChar() { return getToken(PCREParser.ControlChar, 0); }
		public TerminalNode DecimalDigit() { return getToken(PCREParser.DecimalDigit, 0); }
		public TerminalNode NotDecimalDigit() { return getToken(PCREParser.NotDecimalDigit, 0); }
		public TerminalNode HorizontalWhiteSpace() { return getToken(PCREParser.HorizontalWhiteSpace, 0); }
		public TerminalNode NotHorizontalWhiteSpace() { return getToken(PCREParser.NotHorizontalWhiteSpace, 0); }
		public TerminalNode NotNewLine() { return getToken(PCREParser.NotNewLine, 0); }
		public TerminalNode CharWithProperty() { return getToken(PCREParser.CharWithProperty, 0); }
		public TerminalNode CharWithoutProperty() { return getToken(PCREParser.CharWithoutProperty, 0); }
		public TerminalNode NewLineSequence() { return getToken(PCREParser.NewLineSequence, 0); }
		public TerminalNode WhiteSpace() { return getToken(PCREParser.WhiteSpace, 0); }
		public TerminalNode NotWhiteSpace() { return getToken(PCREParser.NotWhiteSpace, 0); }
		public TerminalNode VerticalWhiteSpace() { return getToken(PCREParser.VerticalWhiteSpace, 0); }
		public TerminalNode NotVerticalWhiteSpace() { return getToken(PCREParser.NotVerticalWhiteSpace, 0); }
		public TerminalNode WordChar() { return getToken(PCREParser.WordChar, 0); }
		public TerminalNode NotWordChar() { return getToken(PCREParser.NotWordChar, 0); }
		public TerminalNode Backslash() { return getToken(PCREParser.Backslash, 0); }
		public Shared_atomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shared_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterShared_atom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitShared_atom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitShared_atom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Shared_atomContext shared_atom() throws RecognitionException {
		Shared_atomContext _localctx = new Shared_atomContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_shared_atom);
		try {
			setState(854);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case POSIXNamedSet:
				enterOuterAlt(_localctx, 1);
				{
				setState(835);
				match(POSIXNamedSet);
				}
				break;
			case POSIXNegatedNamedSet:
				enterOuterAlt(_localctx, 2);
				{
				setState(836);
				match(POSIXNegatedNamedSet);
				}
				break;
			case ControlChar:
				enterOuterAlt(_localctx, 3);
				{
				setState(837);
				match(ControlChar);
				}
				break;
			case DecimalDigit:
				enterOuterAlt(_localctx, 4);
				{
				setState(838);
				match(DecimalDigit);
				}
				break;
			case NotDecimalDigit:
				enterOuterAlt(_localctx, 5);
				{
				setState(839);
				match(NotDecimalDigit);
				}
				break;
			case HorizontalWhiteSpace:
				enterOuterAlt(_localctx, 6);
				{
				setState(840);
				match(HorizontalWhiteSpace);
				}
				break;
			case NotHorizontalWhiteSpace:
				enterOuterAlt(_localctx, 7);
				{
				setState(841);
				match(NotHorizontalWhiteSpace);
				}
				break;
			case NotNewLine:
				enterOuterAlt(_localctx, 8);
				{
				setState(842);
				match(NotNewLine);
				}
				break;
			case CharWithProperty:
				enterOuterAlt(_localctx, 9);
				{
				setState(843);
				match(CharWithProperty);
				}
				break;
			case CharWithoutProperty:
				enterOuterAlt(_localctx, 10);
				{
				setState(844);
				match(CharWithoutProperty);
				}
				break;
			case NewLineSequence:
				enterOuterAlt(_localctx, 11);
				{
				setState(845);
				match(NewLineSequence);
				}
				break;
			case WhiteSpace:
				enterOuterAlt(_localctx, 12);
				{
				setState(846);
				match(WhiteSpace);
				}
				break;
			case NotWhiteSpace:
				enterOuterAlt(_localctx, 13);
				{
				setState(847);
				match(NotWhiteSpace);
				}
				break;
			case VerticalWhiteSpace:
				enterOuterAlt(_localctx, 14);
				{
				setState(848);
				match(VerticalWhiteSpace);
				}
				break;
			case NotVerticalWhiteSpace:
				enterOuterAlt(_localctx, 15);
				{
				setState(849);
				match(NotVerticalWhiteSpace);
				}
				break;
			case WordChar:
				enterOuterAlt(_localctx, 16);
				{
				setState(850);
				match(WordChar);
				}
				break;
			case NotWordChar:
				enterOuterAlt(_localctx, 17);
				{
				setState(851);
				match(NotWordChar);
				}
				break;
			case Backslash:
				enterOuterAlt(_localctx, 18);
				{
				setState(852);
				match(Backslash);
				setState(853);
				matchWildcard();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public Shared_literalContext shared_literal() {
			return getRuleContext(Shared_literalContext.class,0);
		}
		public TerminalNode CharacterClassEnd() { return getToken(PCREParser.CharacterClassEnd, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_literal);
		try {
			setState(858);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Quoted:
			case BlockQuoted:
			case BellChar:
			case EscapeChar:
			case FormFeed:
			case NewLine:
			case CarriageReturn:
			case Tab:
			case Backslash:
			case HexChar:
			case Hyphen:
			case OpenBrace:
			case CloseBrace:
			case Comma:
			case LessThan:
			case GreaterThan:
			case SingleQuote:
			case Underscore:
			case Colon:
			case Hash:
			case Equals:
			case Exclamation:
			case Ampersand:
			case ALC:
			case BLC:
			case CLC:
			case DLC:
			case ELC:
			case FLC:
			case GLC:
			case HLC:
			case ILC:
			case JLC:
			case KLC:
			case LLC:
			case MLC:
			case NLC:
			case OLC:
			case PLC:
			case QLC:
			case RLC:
			case SLC:
			case TLC:
			case ULC:
			case VLC:
			case WLC:
			case XLC:
			case YLC:
			case ZLC:
			case AUC:
			case BUC:
			case CUC:
			case DUC:
			case EUC:
			case FUC:
			case GUC:
			case HUC:
			case IUC:
			case JUC:
			case KUC:
			case LUC:
			case MUC:
			case NUC:
			case OUC:
			case PUC:
			case QUC:
			case RUC:
			case SUC:
			case TUC:
			case UUC:
			case VUC:
			case WUC:
			case XUC:
			case YUC:
			case ZUC:
			case D1:
			case D2:
			case D3:
			case D4:
			case D5:
			case D6:
			case D7:
			case D8:
			case D9:
			case D0:
			case OtherChar:
				enterOuterAlt(_localctx, 1);
				{
				setState(856);
				shared_literal();
				}
				break;
			case CharacterClassEnd:
				enterOuterAlt(_localctx, 2);
				{
				setState(857);
				match(CharacterClassEnd);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Cc_literalContext extends ParserRuleContext {
		public Shared_literalContext shared_literal() {
			return getRuleContext(Shared_literalContext.class,0);
		}
		public TerminalNode Dot() { return getToken(PCREParser.Dot, 0); }
		public TerminalNode CharacterClassStart() { return getToken(PCREParser.CharacterClassStart, 0); }
		public TerminalNode Caret() { return getToken(PCREParser.Caret, 0); }
		public TerminalNode QuestionMark() { return getToken(PCREParser.QuestionMark, 0); }
		public TerminalNode Plus() { return getToken(PCREParser.Plus, 0); }
		public TerminalNode Star() { return getToken(PCREParser.Star, 0); }
		public TerminalNode WordBoundary() { return getToken(PCREParser.WordBoundary, 0); }
		public TerminalNode EndOfSubjectOrLine() { return getToken(PCREParser.EndOfSubjectOrLine, 0); }
		public TerminalNode Pipe() { return getToken(PCREParser.Pipe, 0); }
		public TerminalNode OpenParen() { return getToken(PCREParser.OpenParen, 0); }
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public Cc_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cc_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterCc_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitCc_literal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitCc_literal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Cc_literalContext cc_literal() throws RecognitionException {
		Cc_literalContext _localctx = new Cc_literalContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_cc_literal);
		try {
			setState(872);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Quoted:
			case BlockQuoted:
			case BellChar:
			case EscapeChar:
			case FormFeed:
			case NewLine:
			case CarriageReturn:
			case Tab:
			case Backslash:
			case HexChar:
			case Hyphen:
			case OpenBrace:
			case CloseBrace:
			case Comma:
			case LessThan:
			case GreaterThan:
			case SingleQuote:
			case Underscore:
			case Colon:
			case Hash:
			case Equals:
			case Exclamation:
			case Ampersand:
			case ALC:
			case BLC:
			case CLC:
			case DLC:
			case ELC:
			case FLC:
			case GLC:
			case HLC:
			case ILC:
			case JLC:
			case KLC:
			case LLC:
			case MLC:
			case NLC:
			case OLC:
			case PLC:
			case QLC:
			case RLC:
			case SLC:
			case TLC:
			case ULC:
			case VLC:
			case WLC:
			case XLC:
			case YLC:
			case ZLC:
			case AUC:
			case BUC:
			case CUC:
			case DUC:
			case EUC:
			case FUC:
			case GUC:
			case HUC:
			case IUC:
			case JUC:
			case KUC:
			case LUC:
			case MUC:
			case NUC:
			case OUC:
			case PUC:
			case QUC:
			case RUC:
			case SUC:
			case TUC:
			case UUC:
			case VUC:
			case WUC:
			case XUC:
			case YUC:
			case ZUC:
			case D1:
			case D2:
			case D3:
			case D4:
			case D5:
			case D6:
			case D7:
			case D8:
			case D9:
			case D0:
			case OtherChar:
				enterOuterAlt(_localctx, 1);
				{
				setState(860);
				shared_literal();
				}
				break;
			case Dot:
				enterOuterAlt(_localctx, 2);
				{
				setState(861);
				match(Dot);
				}
				break;
			case CharacterClassStart:
				enterOuterAlt(_localctx, 3);
				{
				setState(862);
				match(CharacterClassStart);
				}
				break;
			case Caret:
				enterOuterAlt(_localctx, 4);
				{
				setState(863);
				match(Caret);
				}
				break;
			case QuestionMark:
				enterOuterAlt(_localctx, 5);
				{
				setState(864);
				match(QuestionMark);
				}
				break;
			case Plus:
				enterOuterAlt(_localctx, 6);
				{
				setState(865);
				match(Plus);
				}
				break;
			case Star:
				enterOuterAlt(_localctx, 7);
				{
				setState(866);
				match(Star);
				}
				break;
			case WordBoundary:
				enterOuterAlt(_localctx, 8);
				{
				setState(867);
				match(WordBoundary);
				}
				break;
			case EndOfSubjectOrLine:
				enterOuterAlt(_localctx, 9);
				{
				setState(868);
				match(EndOfSubjectOrLine);
				}
				break;
			case Pipe:
				enterOuterAlt(_localctx, 10);
				{
				setState(869);
				match(Pipe);
				}
				break;
			case OpenParen:
				enterOuterAlt(_localctx, 11);
				{
				setState(870);
				match(OpenParen);
				}
				break;
			case CloseParen:
				enterOuterAlt(_localctx, 12);
				{
				setState(871);
				match(CloseParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Shared_literalContext extends ParserRuleContext {
		public Octal_charContext octal_char() {
			return getRuleContext(Octal_charContext.class,0);
		}
		public LetterContext letter() {
			return getRuleContext(LetterContext.class,0);
		}
		public DigitContext digit() {
			return getRuleContext(DigitContext.class,0);
		}
		public TerminalNode BellChar() { return getToken(PCREParser.BellChar, 0); }
		public TerminalNode EscapeChar() { return getToken(PCREParser.EscapeChar, 0); }
		public TerminalNode FormFeed() { return getToken(PCREParser.FormFeed, 0); }
		public TerminalNode NewLine() { return getToken(PCREParser.NewLine, 0); }
		public TerminalNode CarriageReturn() { return getToken(PCREParser.CarriageReturn, 0); }
		public TerminalNode Tab() { return getToken(PCREParser.Tab, 0); }
		public TerminalNode HexChar() { return getToken(PCREParser.HexChar, 0); }
		public TerminalNode Quoted() { return getToken(PCREParser.Quoted, 0); }
		public TerminalNode BlockQuoted() { return getToken(PCREParser.BlockQuoted, 0); }
		public TerminalNode OpenBrace() { return getToken(PCREParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(PCREParser.CloseBrace, 0); }
		public TerminalNode Comma() { return getToken(PCREParser.Comma, 0); }
		public TerminalNode Hyphen() { return getToken(PCREParser.Hyphen, 0); }
		public TerminalNode LessThan() { return getToken(PCREParser.LessThan, 0); }
		public TerminalNode GreaterThan() { return getToken(PCREParser.GreaterThan, 0); }
		public TerminalNode SingleQuote() { return getToken(PCREParser.SingleQuote, 0); }
		public TerminalNode Underscore() { return getToken(PCREParser.Underscore, 0); }
		public TerminalNode Colon() { return getToken(PCREParser.Colon, 0); }
		public TerminalNode Hash() { return getToken(PCREParser.Hash, 0); }
		public TerminalNode Equals() { return getToken(PCREParser.Equals, 0); }
		public TerminalNode Exclamation() { return getToken(PCREParser.Exclamation, 0); }
		public TerminalNode Ampersand() { return getToken(PCREParser.Ampersand, 0); }
		public TerminalNode OtherChar() { return getToken(PCREParser.OtherChar, 0); }
		public Shared_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shared_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterShared_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitShared_literal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitShared_literal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Shared_literalContext shared_literal() throws RecognitionException {
		Shared_literalContext _localctx = new Shared_literalContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_shared_literal);
		try {
			setState(900);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Backslash:
				enterOuterAlt(_localctx, 1);
				{
				setState(874);
				octal_char();
				}
				break;
			case ALC:
			case BLC:
			case CLC:
			case DLC:
			case ELC:
			case FLC:
			case GLC:
			case HLC:
			case ILC:
			case JLC:
			case KLC:
			case LLC:
			case MLC:
			case NLC:
			case OLC:
			case PLC:
			case QLC:
			case RLC:
			case SLC:
			case TLC:
			case ULC:
			case VLC:
			case WLC:
			case XLC:
			case YLC:
			case ZLC:
			case AUC:
			case BUC:
			case CUC:
			case DUC:
			case EUC:
			case FUC:
			case GUC:
			case HUC:
			case IUC:
			case JUC:
			case KUC:
			case LUC:
			case MUC:
			case NUC:
			case OUC:
			case PUC:
			case QUC:
			case RUC:
			case SUC:
			case TUC:
			case UUC:
			case VUC:
			case WUC:
			case XUC:
			case YUC:
			case ZUC:
				enterOuterAlt(_localctx, 2);
				{
				setState(875);
				letter();
				}
				break;
			case D1:
			case D2:
			case D3:
			case D4:
			case D5:
			case D6:
			case D7:
			case D8:
			case D9:
			case D0:
				enterOuterAlt(_localctx, 3);
				{
				setState(876);
				digit();
				}
				break;
			case BellChar:
				enterOuterAlt(_localctx, 4);
				{
				setState(877);
				match(BellChar);
				}
				break;
			case EscapeChar:
				enterOuterAlt(_localctx, 5);
				{
				setState(878);
				match(EscapeChar);
				}
				break;
			case FormFeed:
				enterOuterAlt(_localctx, 6);
				{
				setState(879);
				match(FormFeed);
				}
				break;
			case NewLine:
				enterOuterAlt(_localctx, 7);
				{
				setState(880);
				match(NewLine);
				}
				break;
			case CarriageReturn:
				enterOuterAlt(_localctx, 8);
				{
				setState(881);
				match(CarriageReturn);
				}
				break;
			case Tab:
				enterOuterAlt(_localctx, 9);
				{
				setState(882);
				match(Tab);
				}
				break;
			case HexChar:
				enterOuterAlt(_localctx, 10);
				{
				setState(883);
				match(HexChar);
				}
				break;
			case Quoted:
				enterOuterAlt(_localctx, 11);
				{
				setState(884);
				match(Quoted);
				}
				break;
			case BlockQuoted:
				enterOuterAlt(_localctx, 12);
				{
				setState(885);
				match(BlockQuoted);
				}
				break;
			case OpenBrace:
				enterOuterAlt(_localctx, 13);
				{
				setState(886);
				match(OpenBrace);
				}
				break;
			case CloseBrace:
				enterOuterAlt(_localctx, 14);
				{
				setState(887);
				match(CloseBrace);
				}
				break;
			case Comma:
				enterOuterAlt(_localctx, 15);
				{
				setState(888);
				match(Comma);
				}
				break;
			case Hyphen:
				enterOuterAlt(_localctx, 16);
				{
				setState(889);
				match(Hyphen);
				}
				break;
			case LessThan:
				enterOuterAlt(_localctx, 17);
				{
				setState(890);
				match(LessThan);
				}
				break;
			case GreaterThan:
				enterOuterAlt(_localctx, 18);
				{
				setState(891);
				match(GreaterThan);
				}
				break;
			case SingleQuote:
				enterOuterAlt(_localctx, 19);
				{
				setState(892);
				match(SingleQuote);
				}
				break;
			case Underscore:
				enterOuterAlt(_localctx, 20);
				{
				setState(893);
				match(Underscore);
				}
				break;
			case Colon:
				enterOuterAlt(_localctx, 21);
				{
				setState(894);
				match(Colon);
				}
				break;
			case Hash:
				enterOuterAlt(_localctx, 22);
				{
				setState(895);
				match(Hash);
				}
				break;
			case Equals:
				enterOuterAlt(_localctx, 23);
				{
				setState(896);
				match(Equals);
				}
				break;
			case Exclamation:
				enterOuterAlt(_localctx, 24);
				{
				setState(897);
				match(Exclamation);
				}
				break;
			case Ampersand:
				enterOuterAlt(_localctx, 25);
				{
				setState(898);
				match(Ampersand);
				}
				break;
			case OtherChar:
				enterOuterAlt(_localctx, 26);
				{
				setState(899);
				match(OtherChar);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public DigitsContext digits() {
			return getRuleContext(DigitsContext.class,0);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(902);
			digits();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Octal_charContext extends ParserRuleContext {
		public TerminalNode Backslash() { return getToken(PCREParser.Backslash, 0); }
		public List<Octal_digitContext> octal_digit() {
			return getRuleContexts(Octal_digitContext.class);
		}
		public Octal_digitContext octal_digit(int i) {
			return getRuleContext(Octal_digitContext.class,i);
		}
		public TerminalNode D0() { return getToken(PCREParser.D0, 0); }
		public TerminalNode D1() { return getToken(PCREParser.D1, 0); }
		public TerminalNode D2() { return getToken(PCREParser.D2, 0); }
		public TerminalNode D3() { return getToken(PCREParser.D3, 0); }
		public Octal_charContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_octal_char; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterOctal_char(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitOctal_char(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitOctal_char(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Octal_charContext octal_char() throws RecognitionException {
		Octal_charContext _localctx = new Octal_charContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_octal_char);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(913);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				{
				setState(904);
				match(Backslash);
				setState(905);
				_la = _input.LA(1);
				if ( !(((((_la - 115)) & ~0x3f) == 0 && ((1L << (_la - 115)) & ((1L << (D1 - 115)) | (1L << (D2 - 115)) | (1L << (D3 - 115)) | (1L << (D0 - 115)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(906);
				octal_digit();
				setState(907);
				octal_digit();
				}
				break;
			case 2:
				{
				setState(909);
				match(Backslash);
				setState(910);
				octal_digit();
				setState(911);
				octal_digit();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Octal_digitContext extends ParserRuleContext {
		public TerminalNode D0() { return getToken(PCREParser.D0, 0); }
		public TerminalNode D1() { return getToken(PCREParser.D1, 0); }
		public TerminalNode D2() { return getToken(PCREParser.D2, 0); }
		public TerminalNode D3() { return getToken(PCREParser.D3, 0); }
		public TerminalNode D4() { return getToken(PCREParser.D4, 0); }
		public TerminalNode D5() { return getToken(PCREParser.D5, 0); }
		public TerminalNode D6() { return getToken(PCREParser.D6, 0); }
		public TerminalNode D7() { return getToken(PCREParser.D7, 0); }
		public Octal_digitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_octal_digit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterOctal_digit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitOctal_digit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitOctal_digit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Octal_digitContext octal_digit() throws RecognitionException {
		Octal_digitContext _localctx = new Octal_digitContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_octal_digit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(915);
			_la = _input.LA(1);
			if ( !(((((_la - 115)) & ~0x3f) == 0 && ((1L << (_la - 115)) & ((1L << (D1 - 115)) | (1L << (D2 - 115)) | (1L << (D3 - 115)) | (1L << (D4 - 115)) | (1L << (D5 - 115)) | (1L << (D6 - 115)) | (1L << (D7 - 115)) | (1L << (D0 - 115)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DigitsContext extends ParserRuleContext {
		public List<DigitContext> digit() {
			return getRuleContexts(DigitContext.class);
		}
		public DigitContext digit(int i) {
			return getRuleContext(DigitContext.class,i);
		}
		public DigitsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_digits; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterDigits(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitDigits(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitDigits(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DigitsContext digits() throws RecognitionException {
		DigitsContext _localctx = new DigitsContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_digits);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(918); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(917);
					digit();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(920); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DigitContext extends ParserRuleContext {
		public TerminalNode D0() { return getToken(PCREParser.D0, 0); }
		public TerminalNode D1() { return getToken(PCREParser.D1, 0); }
		public TerminalNode D2() { return getToken(PCREParser.D2, 0); }
		public TerminalNode D3() { return getToken(PCREParser.D3, 0); }
		public TerminalNode D4() { return getToken(PCREParser.D4, 0); }
		public TerminalNode D5() { return getToken(PCREParser.D5, 0); }
		public TerminalNode D6() { return getToken(PCREParser.D6, 0); }
		public TerminalNode D7() { return getToken(PCREParser.D7, 0); }
		public TerminalNode D8() { return getToken(PCREParser.D8, 0); }
		public TerminalNode D9() { return getToken(PCREParser.D9, 0); }
		public DigitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_digit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterDigit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitDigit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitDigit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DigitContext digit() throws RecognitionException {
		DigitContext _localctx = new DigitContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_digit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(922);
			_la = _input.LA(1);
			if ( !(((((_la - 115)) & ~0x3f) == 0 && ((1L << (_la - 115)) & ((1L << (D1 - 115)) | (1L << (D2 - 115)) | (1L << (D3 - 115)) | (1L << (D4 - 115)) | (1L << (D5 - 115)) | (1L << (D6 - 115)) | (1L << (D7 - 115)) | (1L << (D8 - 115)) | (1L << (D9 - 115)) | (1L << (D0 - 115)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public Alpha_numsContext alpha_nums() {
			return getRuleContext(Alpha_numsContext.class,0);
		}
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(924);
			alpha_nums();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Alpha_numsContext extends ParserRuleContext {
		public List<LetterContext> letter() {
			return getRuleContexts(LetterContext.class);
		}
		public LetterContext letter(int i) {
			return getRuleContext(LetterContext.class,i);
		}
		public List<TerminalNode> Underscore() { return getTokens(PCREParser.Underscore); }
		public TerminalNode Underscore(int i) {
			return getToken(PCREParser.Underscore, i);
		}
		public List<DigitContext> digit() {
			return getRuleContexts(DigitContext.class);
		}
		public DigitContext digit(int i) {
			return getRuleContext(DigitContext.class,i);
		}
		public Alpha_numsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alpha_nums; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterAlpha_nums(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitAlpha_nums(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitAlpha_nums(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Alpha_numsContext alpha_nums() throws RecognitionException {
		Alpha_numsContext _localctx = new Alpha_numsContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_alpha_nums);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(928);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ALC:
			case BLC:
			case CLC:
			case DLC:
			case ELC:
			case FLC:
			case GLC:
			case HLC:
			case ILC:
			case JLC:
			case KLC:
			case LLC:
			case MLC:
			case NLC:
			case OLC:
			case PLC:
			case QLC:
			case RLC:
			case SLC:
			case TLC:
			case ULC:
			case VLC:
			case WLC:
			case XLC:
			case YLC:
			case ZLC:
			case AUC:
			case BUC:
			case CUC:
			case DUC:
			case EUC:
			case FUC:
			case GUC:
			case HUC:
			case IUC:
			case JUC:
			case KUC:
			case LUC:
			case MUC:
			case NUC:
			case OUC:
			case PUC:
			case QUC:
			case RUC:
			case SUC:
			case TUC:
			case UUC:
			case VUC:
			case WUC:
			case XUC:
			case YUC:
			case ZUC:
				{
				setState(926);
				letter();
				}
				break;
			case Underscore:
				{
				setState(927);
				match(Underscore);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(935);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Underscore || _la==ALC || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)))) != 0)) {
				{
				setState(933);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ALC:
				case BLC:
				case CLC:
				case DLC:
				case ELC:
				case FLC:
				case GLC:
				case HLC:
				case ILC:
				case JLC:
				case KLC:
				case LLC:
				case MLC:
				case NLC:
				case OLC:
				case PLC:
				case QLC:
				case RLC:
				case SLC:
				case TLC:
				case ULC:
				case VLC:
				case WLC:
				case XLC:
				case YLC:
				case ZLC:
				case AUC:
				case BUC:
				case CUC:
				case DUC:
				case EUC:
				case FUC:
				case GUC:
				case HUC:
				case IUC:
				case JUC:
				case KUC:
				case LUC:
				case MUC:
				case NUC:
				case OUC:
				case PUC:
				case QUC:
				case RUC:
				case SUC:
				case TUC:
				case UUC:
				case VUC:
				case WUC:
				case XUC:
				case YUC:
				case ZUC:
					{
					setState(930);
					letter();
					}
					break;
				case Underscore:
					{
					setState(931);
					match(Underscore);
					}
					break;
				case D1:
				case D2:
				case D3:
				case D4:
				case D5:
				case D6:
				case D7:
				case D8:
				case D9:
				case D0:
					{
					setState(932);
					digit();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(937);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Non_close_parensContext extends ParserRuleContext {
		public List<Non_close_parenContext> non_close_paren() {
			return getRuleContexts(Non_close_parenContext.class);
		}
		public Non_close_parenContext non_close_paren(int i) {
			return getRuleContext(Non_close_parenContext.class,i);
		}
		public Non_close_parensContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_non_close_parens; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterNon_close_parens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitNon_close_parens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitNon_close_parens(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Non_close_parensContext non_close_parens() throws RecognitionException {
		Non_close_parensContext _localctx = new Non_close_parensContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_non_close_parens);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(939); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(938);
				non_close_paren();
				}
				}
				setState(941); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Quoted) | (1L << BlockQuoted) | (1L << BellChar) | (1L << ControlChar) | (1L << EscapeChar) | (1L << FormFeed) | (1L << NewLine) | (1L << CarriageReturn) | (1L << Tab) | (1L << Backslash) | (1L << HexChar) | (1L << Dot) | (1L << OneDataUnit) | (1L << DecimalDigit) | (1L << NotDecimalDigit) | (1L << HorizontalWhiteSpace) | (1L << NotHorizontalWhiteSpace) | (1L << NotNewLine) | (1L << CharWithProperty) | (1L << CharWithoutProperty) | (1L << NewLineSequence) | (1L << WhiteSpace) | (1L << NotWhiteSpace) | (1L << VerticalWhiteSpace) | (1L << NotVerticalWhiteSpace) | (1L << WordChar) | (1L << NotWordChar) | (1L << ExtendedUnicodeChar) | (1L << CharacterClassStart) | (1L << CharacterClassEnd) | (1L << Caret) | (1L << Hyphen) | (1L << POSIXNamedSet) | (1L << POSIXNegatedNamedSet) | (1L << QuestionMark) | (1L << Plus) | (1L << Star) | (1L << OpenBrace) | (1L << CloseBrace) | (1L << Comma) | (1L << WordBoundary) | (1L << NonWordBoundary) | (1L << StartOfSubject) | (1L << EndOfSubjectOrLine) | (1L << EndOfSubjectOrLineEndOfSubject) | (1L << EndOfSubject) | (1L << PreviousMatchInSubject) | (1L << ResetStartMatch) | (1L << SubroutineOrNamedReferenceStartG) | (1L << NamedReferenceStartK) | (1L << Pipe) | (1L << OpenParen) | (1L << LessThan) | (1L << GreaterThan) | (1L << SingleQuote) | (1L << Underscore) | (1L << Colon) | (1L << Hash) | (1L << Equals) | (1L << Exclamation) | (1L << Ampersand) | (1L << ALC))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BLC - 64)) | (1L << (CLC - 64)) | (1L << (DLC - 64)) | (1L << (ELC - 64)) | (1L << (FLC - 64)) | (1L << (GLC - 64)) | (1L << (HLC - 64)) | (1L << (ILC - 64)) | (1L << (JLC - 64)) | (1L << (KLC - 64)) | (1L << (LLC - 64)) | (1L << (MLC - 64)) | (1L << (NLC - 64)) | (1L << (OLC - 64)) | (1L << (PLC - 64)) | (1L << (QLC - 64)) | (1L << (RLC - 64)) | (1L << (SLC - 64)) | (1L << (TLC - 64)) | (1L << (ULC - 64)) | (1L << (VLC - 64)) | (1L << (WLC - 64)) | (1L << (XLC - 64)) | (1L << (YLC - 64)) | (1L << (ZLC - 64)) | (1L << (AUC - 64)) | (1L << (BUC - 64)) | (1L << (CUC - 64)) | (1L << (DUC - 64)) | (1L << (EUC - 64)) | (1L << (FUC - 64)) | (1L << (GUC - 64)) | (1L << (HUC - 64)) | (1L << (IUC - 64)) | (1L << (JUC - 64)) | (1L << (KUC - 64)) | (1L << (LUC - 64)) | (1L << (MUC - 64)) | (1L << (NUC - 64)) | (1L << (OUC - 64)) | (1L << (PUC - 64)) | (1L << (QUC - 64)) | (1L << (RUC - 64)) | (1L << (SUC - 64)) | (1L << (TUC - 64)) | (1L << (UUC - 64)) | (1L << (VUC - 64)) | (1L << (WUC - 64)) | (1L << (XUC - 64)) | (1L << (YUC - 64)) | (1L << (ZUC - 64)) | (1L << (D1 - 64)) | (1L << (D2 - 64)) | (1L << (D3 - 64)) | (1L << (D4 - 64)) | (1L << (D5 - 64)) | (1L << (D6 - 64)) | (1L << (D7 - 64)) | (1L << (D8 - 64)) | (1L << (D9 - 64)) | (1L << (D0 - 64)) | (1L << (OtherChar - 64)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Non_close_parenContext extends ParserRuleContext {
		public TerminalNode CloseParen() { return getToken(PCREParser.CloseParen, 0); }
		public Non_close_parenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_non_close_paren; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterNon_close_paren(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitNon_close_paren(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitNon_close_paren(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Non_close_parenContext non_close_paren() throws RecognitionException {
		Non_close_parenContext _localctx = new Non_close_parenContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_non_close_paren);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(943);
			_la = _input.LA(1);
			if ( _la <= 0 || (_la==CloseParen) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LetterContext extends ParserRuleContext {
		public TerminalNode ALC() { return getToken(PCREParser.ALC, 0); }
		public TerminalNode BLC() { return getToken(PCREParser.BLC, 0); }
		public TerminalNode CLC() { return getToken(PCREParser.CLC, 0); }
		public TerminalNode DLC() { return getToken(PCREParser.DLC, 0); }
		public TerminalNode ELC() { return getToken(PCREParser.ELC, 0); }
		public TerminalNode FLC() { return getToken(PCREParser.FLC, 0); }
		public TerminalNode GLC() { return getToken(PCREParser.GLC, 0); }
		public TerminalNode HLC() { return getToken(PCREParser.HLC, 0); }
		public TerminalNode ILC() { return getToken(PCREParser.ILC, 0); }
		public TerminalNode JLC() { return getToken(PCREParser.JLC, 0); }
		public TerminalNode KLC() { return getToken(PCREParser.KLC, 0); }
		public TerminalNode LLC() { return getToken(PCREParser.LLC, 0); }
		public TerminalNode MLC() { return getToken(PCREParser.MLC, 0); }
		public TerminalNode NLC() { return getToken(PCREParser.NLC, 0); }
		public TerminalNode OLC() { return getToken(PCREParser.OLC, 0); }
		public TerminalNode PLC() { return getToken(PCREParser.PLC, 0); }
		public TerminalNode QLC() { return getToken(PCREParser.QLC, 0); }
		public TerminalNode RLC() { return getToken(PCREParser.RLC, 0); }
		public TerminalNode SLC() { return getToken(PCREParser.SLC, 0); }
		public TerminalNode TLC() { return getToken(PCREParser.TLC, 0); }
		public TerminalNode ULC() { return getToken(PCREParser.ULC, 0); }
		public TerminalNode VLC() { return getToken(PCREParser.VLC, 0); }
		public TerminalNode WLC() { return getToken(PCREParser.WLC, 0); }
		public TerminalNode XLC() { return getToken(PCREParser.XLC, 0); }
		public TerminalNode YLC() { return getToken(PCREParser.YLC, 0); }
		public TerminalNode ZLC() { return getToken(PCREParser.ZLC, 0); }
		public TerminalNode AUC() { return getToken(PCREParser.AUC, 0); }
		public TerminalNode BUC() { return getToken(PCREParser.BUC, 0); }
		public TerminalNode CUC() { return getToken(PCREParser.CUC, 0); }
		public TerminalNode DUC() { return getToken(PCREParser.DUC, 0); }
		public TerminalNode EUC() { return getToken(PCREParser.EUC, 0); }
		public TerminalNode FUC() { return getToken(PCREParser.FUC, 0); }
		public TerminalNode GUC() { return getToken(PCREParser.GUC, 0); }
		public TerminalNode HUC() { return getToken(PCREParser.HUC, 0); }
		public TerminalNode IUC() { return getToken(PCREParser.IUC, 0); }
		public TerminalNode JUC() { return getToken(PCREParser.JUC, 0); }
		public TerminalNode KUC() { return getToken(PCREParser.KUC, 0); }
		public TerminalNode LUC() { return getToken(PCREParser.LUC, 0); }
		public TerminalNode MUC() { return getToken(PCREParser.MUC, 0); }
		public TerminalNode NUC() { return getToken(PCREParser.NUC, 0); }
		public TerminalNode OUC() { return getToken(PCREParser.OUC, 0); }
		public TerminalNode PUC() { return getToken(PCREParser.PUC, 0); }
		public TerminalNode QUC() { return getToken(PCREParser.QUC, 0); }
		public TerminalNode RUC() { return getToken(PCREParser.RUC, 0); }
		public TerminalNode SUC() { return getToken(PCREParser.SUC, 0); }
		public TerminalNode TUC() { return getToken(PCREParser.TUC, 0); }
		public TerminalNode UUC() { return getToken(PCREParser.UUC, 0); }
		public TerminalNode VUC() { return getToken(PCREParser.VUC, 0); }
		public TerminalNode WUC() { return getToken(PCREParser.WUC, 0); }
		public TerminalNode XUC() { return getToken(PCREParser.XUC, 0); }
		public TerminalNode YUC() { return getToken(PCREParser.YUC, 0); }
		public TerminalNode ZUC() { return getToken(PCREParser.ZUC, 0); }
		public LetterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).enterLetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PCREListener ) ((PCREListener)listener).exitLetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCREVisitor ) return ((PCREVisitor<? extends T>)visitor).visitLetter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LetterContext letter() throws RecognitionException {
		LetterContext _localctx = new LetterContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_letter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(945);
			_la = _input.LA(1);
			if ( !(((((_la - 63)) & ~0x3f) == 0 && ((1L << (_la - 63)) & ((1L << (ALC - 63)) | (1L << (BLC - 63)) | (1L << (CLC - 63)) | (1L << (DLC - 63)) | (1L << (ELC - 63)) | (1L << (FLC - 63)) | (1L << (GLC - 63)) | (1L << (HLC - 63)) | (1L << (ILC - 63)) | (1L << (JLC - 63)) | (1L << (KLC - 63)) | (1L << (LLC - 63)) | (1L << (MLC - 63)) | (1L << (NLC - 63)) | (1L << (OLC - 63)) | (1L << (PLC - 63)) | (1L << (QLC - 63)) | (1L << (RLC - 63)) | (1L << (SLC - 63)) | (1L << (TLC - 63)) | (1L << (ULC - 63)) | (1L << (VLC - 63)) | (1L << (WLC - 63)) | (1L << (XLC - 63)) | (1L << (YLC - 63)) | (1L << (ZLC - 63)) | (1L << (AUC - 63)) | (1L << (BUC - 63)) | (1L << (CUC - 63)) | (1L << (DUC - 63)) | (1L << (EUC - 63)) | (1L << (FUC - 63)) | (1L << (GUC - 63)) | (1L << (HUC - 63)) | (1L << (IUC - 63)) | (1L << (JUC - 63)) | (1L << (KUC - 63)) | (1L << (LUC - 63)) | (1L << (MUC - 63)) | (1L << (NUC - 63)) | (1L << (OUC - 63)) | (1L << (PUC - 63)) | (1L << (QUC - 63)) | (1L << (RUC - 63)) | (1L << (SUC - 63)) | (1L << (TUC - 63)) | (1L << (UUC - 63)) | (1L << (VUC - 63)) | (1L << (WUC - 63)) | (1L << (XUC - 63)) | (1L << (YUC - 63)) | (1L << (ZUC - 63)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\177\u03b6\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\2\3\3\3\3\3\3\7\3S\n\3\f"+
		"\3\16\3V\13\3\3\4\7\4Y\n\4\f\4\16\4\\\13\4\3\5\3\5\5\5`\n\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\5\6z\n\6\3\7\3\7\3\7\5\7\177\n\7\3\b\3\b\3\b\3\b\3\b"+
		"\6\b\u0086\n\b\r\b\16\b\u0087\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u0090\n\b\f"+
		"\b\16\b\u0093\13\b\3\b\3\b\3\b\3\b\6\b\u0099\n\b\r\b\16\b\u009a\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\6\b\u00a3\n\b\r\b\16\b\u00a4\3\b\3\b\3\b\3\b\3\b\7"+
		"\b\u00ac\n\b\f\b\16\b\u00af\13\b\3\b\3\b\3\b\6\b\u00b4\n\b\r\b\16\b\u00b5"+
		"\3\b\3\b\5\b\u00ba\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00e5\n\t\3\n\3\n\3"+
		"\n\5\n\u00ea\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\5\13\u0109\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f"+
		"\u0124\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\5\16\u0162\n\16\3\17\6\17\u0165\n\17\r\17\16"+
		"\17\u0166\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\5\21\u0185\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22"+
		"\u01d5\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u01df\n\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u01ec\n\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u01f9\n\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0207"+
		"\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23"+
		"\u0215\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23"+
		"\u0222\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u022e"+
		"\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23"+
		"\u023c\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\5\23\u024d\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u025e\n\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u026a\n\23\3\23\3\23\5\23\u026e\n"+
		"\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\5\24\u027f\n\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0288"+
		"\n\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\5\24\u02d4\n\24\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25"+
		"\u0313\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u031f"+
		"\n\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27"+
		"\u033b\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0344\n\30\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\5\31\u0359\n\31\3\32\3\32\5\32\u035d\n\32\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u036b\n\33\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u0387\n\34"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u0394\n\36"+
		"\3\37\3\37\3 \6 \u0399\n \r \16 \u039a\3!\3!\3\"\3\"\3#\3#\5#\u03a3\n"+
		"#\3#\3#\3#\7#\u03a8\n#\f#\16#\u03ab\13#\3$\6$\u03ae\n$\r$\16$\u03af\3"+
		"%\3%\3&\3&\3&\2\2\'\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60"+
		"\62\64\668:<>@BDFHJ\2\b\b\2IIMMSSXXddoo\4\2uw~~\4\2u{~~\3\2u~\3\2\67\67"+
		"\3\2At\2\u044b\2L\3\2\2\2\4O\3\2\2\2\6Z\3\2\2\2\b]\3\2\2\2\ny\3\2\2\2"+
		"\f~\3\2\2\2\16\u00b9\3\2\2\2\20\u00e4\3\2\2\2\22\u00e9\3\2\2\2\24\u0108"+
		"\3\2\2\2\26\u0123\3\2\2\2\30\u0125\3\2\2\2\32\u0161\3\2\2\2\34\u0164\3"+
		"\2\2\2\36\u0168\3\2\2\2 \u0184\3\2\2\2\"\u01d4\3\2\2\2$\u026d\3\2\2\2"+
		"&\u02d3\3\2\2\2(\u0312\3\2\2\2*\u031e\3\2\2\2,\u033a\3\2\2\2.\u0343\3"+
		"\2\2\2\60\u0358\3\2\2\2\62\u035c\3\2\2\2\64\u036a\3\2\2\2\66\u0386\3\2"+
		"\2\28\u0388\3\2\2\2:\u0393\3\2\2\2<\u0395\3\2\2\2>\u0398\3\2\2\2@\u039c"+
		"\3\2\2\2B\u039e\3\2\2\2D\u03a2\3\2\2\2F\u03ad\3\2\2\2H\u03b1\3\2\2\2J"+
		"\u03b3\3\2\2\2LM\5\4\3\2MN\7\2\2\3N\3\3\2\2\2OT\5\6\4\2PQ\7\65\2\2QS\5"+
		"\6\4\2RP\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2U\5\3\2\2\2VT\3\2\2\2WY"+
		"\5\b\5\2XW\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[\7\3\2\2\2\\Z\3\2\2"+
		"\2]_\5,\27\2^`\5\n\6\2_^\3\2\2\2_`\3\2\2\2`\t\3\2\2\2ab\7%\2\2bz\5\f\7"+
		"\2cd\7&\2\2dz\5\f\7\2ef\7\'\2\2fz\5\f\7\2gh\7(\2\2hi\58\35\2ij\7)\2\2"+
		"jk\5\f\7\2kz\3\2\2\2lm\7(\2\2mn\58\35\2no\7*\2\2op\7)\2\2pq\5\f\7\2qz"+
		"\3\2\2\2rs\7(\2\2st\58\35\2tu\7*\2\2uv\58\35\2vw\7)\2\2wx\5\f\7\2xz\3"+
		"\2\2\2ya\3\2\2\2yc\3\2\2\2ye\3\2\2\2yg\3\2\2\2yl\3\2\2\2yr\3\2\2\2z\13"+
		"\3\2\2\2{\177\7&\2\2|\177\7%\2\2}\177\3\2\2\2~{\3\2\2\2~|\3\2\2\2~}\3"+
		"\2\2\2\177\r\3\2\2\2\u0080\u0081\7\37\2\2\u0081\u0082\7!\2\2\u0082\u0083"+
		"\7 \2\2\u0083\u0085\7\"\2\2\u0084\u0086\5.\30\2\u0085\u0084\3\2\2\2\u0086"+
		"\u0087\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\3\2"+
		"\2\2\u0089\u008a\7 \2\2\u008a\u00ba\3\2\2\2\u008b\u008c\7\37\2\2\u008c"+
		"\u008d\7!\2\2\u008d\u0091\7 \2\2\u008e\u0090\5.\30\2\u008f\u008e\3\2\2"+
		"\2\u0090\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0094"+
		"\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u00ba\7 \2\2\u0095\u0096\7\37\2\2\u0096"+
		"\u0098\7!\2\2\u0097\u0099\5.\30\2\u0098\u0097\3\2\2\2\u0099\u009a\3\2"+
		"\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009c\3\2\2\2\u009c"+
		"\u009d\7 \2\2\u009d\u00ba\3\2\2\2\u009e\u009f\7\37\2\2\u009f\u00a0\7 "+
		"\2\2\u00a0\u00a2\7\"\2\2\u00a1\u00a3\5.\30\2\u00a2\u00a1\3\2\2\2\u00a3"+
		"\u00a4\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\3\2"+
		"\2\2\u00a6\u00a7\7 \2\2\u00a7\u00ba\3\2\2\2\u00a8\u00a9\7\37\2\2\u00a9"+
		"\u00ad\7 \2\2\u00aa\u00ac\5.\30\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2"+
		"\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af"+
		"\u00ad\3\2\2\2\u00b0\u00ba\7 \2\2\u00b1\u00b3\7\37\2\2\u00b2\u00b4\5."+
		"\30\2\u00b3\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b5"+
		"\u00b6\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b8\7 \2\2\u00b8\u00ba\3\2"+
		"\2\2\u00b9\u0080\3\2\2\2\u00b9\u008b\3\2\2\2\u00b9\u0095\3\2\2\2\u00b9"+
		"\u009e\3\2\2\2\u00b9\u00a8\3\2\2\2\u00b9\u00b1\3\2\2\2\u00ba\17\3\2\2"+
		"\2\u00bb\u00e5\5\22\n\2\u00bc\u00bd\7\63\2\2\u00bd\u00e5\58\35\2\u00be"+
		"\u00bf\7\63\2\2\u00bf\u00c0\7(\2\2\u00c0\u00c1\58\35\2\u00c1\u00c2\7)"+
		"\2\2\u00c2\u00e5\3\2\2\2\u00c3\u00c4\7\63\2\2\u00c4\u00c5\7(\2\2\u00c5"+
		"\u00c6\7\"\2\2\u00c6\u00c7\58\35\2\u00c7\u00c8\7)\2\2\u00c8\u00e5\3\2"+
		"\2\2\u00c9\u00ca\7\64\2\2\u00ca\u00cb\78\2\2\u00cb\u00cc\5B\"\2\u00cc"+
		"\u00cd\79\2\2\u00cd\u00e5\3\2\2\2\u00ce\u00cf\7\64\2\2\u00cf\u00d0\7:"+
		"\2\2\u00d0\u00d1\5B\"\2\u00d1\u00d2\7:\2\2\u00d2\u00e5\3\2\2\2\u00d3\u00d4"+
		"\7\63\2\2\u00d4\u00d5\7(\2\2\u00d5\u00d6\5B\"\2\u00d6\u00d7\7)\2\2\u00d7"+
		"\u00e5\3\2\2\2\u00d8\u00d9\7\64\2\2\u00d9\u00da\7(\2\2\u00da\u00db\5B"+
		"\"\2\u00db\u00dc\7)\2\2\u00dc\u00e5\3\2\2\2\u00dd\u00de\7\66\2\2\u00de"+
		"\u00df\7%\2\2\u00df\u00e0\7j\2\2\u00e0\u00e1\7>\2\2\u00e1\u00e2\5B\"\2"+
		"\u00e2\u00e3\7\67\2\2\u00e3\u00e5\3\2\2\2\u00e4\u00bb\3\2\2\2\u00e4\u00bc"+
		"\3\2\2\2\u00e4\u00be\3\2\2\2\u00e4\u00c3\3\2\2\2\u00e4\u00c9\3\2\2\2\u00e4"+
		"\u00ce\3\2\2\2\u00e4\u00d3\3\2\2\2\u00e4\u00d8\3\2\2\2\u00e4\u00dd\3\2"+
		"\2\2\u00e5\21\3\2\2\2\u00e6\u00ea\5:\36\2\u00e7\u00e8\7\f\2\2\u00e8\u00ea"+
		"\5@!\2\u00e9\u00e6\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\23\3\2\2\2\u00eb"+
		"\u00ec\7\66\2\2\u00ec\u00ed\7%\2\2\u00ed\u00ee\78\2\2\u00ee\u00ef\5B\""+
		"\2\u00ef\u00f0\79\2\2\u00f0\u00f1\5\4\3\2\u00f1\u00f2\7\67\2\2\u00f2\u0109"+
		"\3\2\2\2\u00f3\u00f4\7\66\2\2\u00f4\u00f5\7%\2\2\u00f5\u00f6\7:\2\2\u00f6"+
		"\u00f7\5B\"\2\u00f7\u00f8\7:\2\2\u00f8\u00f9\5\4\3\2\u00f9\u00fa\7\67"+
		"\2\2\u00fa\u0109\3\2\2\2\u00fb\u00fc\7\66\2\2\u00fc\u00fd\7%\2\2\u00fd"+
		"\u00fe\7j\2\2\u00fe\u00ff\78\2\2\u00ff\u0100\5B\"\2\u0100\u0101\79\2\2"+
		"\u0101\u0102\5\4\3\2\u0102\u0103\7\67\2\2\u0103\u0109\3\2\2\2\u0104\u0105"+
		"\7\66\2\2\u0105\u0106\5\4\3\2\u0106\u0107\7\67\2\2\u0107\u0109\3\2\2\2"+
		"\u0108\u00eb\3\2\2\2\u0108\u00f3\3\2\2\2\u0108\u00fb\3\2\2\2\u0108\u0104"+
		"\3\2\2\2\u0109\25\3\2\2\2\u010a\u010b\7\66\2\2\u010b\u010c\7%\2\2\u010c"+
		"\u010d\7<\2\2\u010d\u010e\5\4\3\2\u010e\u010f\7\67\2\2\u010f\u0124\3\2"+
		"\2\2\u0110\u0111\7\66\2\2\u0111\u0112\7%\2\2\u0112\u0113\7\65\2\2\u0113"+
		"\u0114\5\4\3\2\u0114\u0115\7\67\2\2\u0115\u0124\3\2\2\2\u0116\u0117\7"+
		"\66\2\2\u0117\u0118\7%\2\2\u0118\u0119\79\2\2\u0119\u011a\5\4\3\2\u011a"+
		"\u011b\7\67\2\2\u011b\u0124\3\2\2\2\u011c\u011d\7\66\2\2\u011d\u011e\7"+
		"%\2\2\u011e\u011f\5\34\17\2\u011f\u0120\7<\2\2\u0120\u0121\5\4\3\2\u0121"+
		"\u0122\7\67\2\2\u0122\u0124\3\2\2\2\u0123\u010a\3\2\2\2\u0123\u0110\3"+
		"\2\2\2\u0123\u0116\3\2\2\2\u0123\u011c\3\2\2\2\u0124\27\3\2\2\2\u0125"+
		"\u0126\7\66\2\2\u0126\u0127\7%\2\2\u0127\u0128\7=\2\2\u0128\u0129\5F$"+
		"\2\u0129\u012a\7\67\2\2\u012a\31\3\2\2\2\u012b\u012c\7\66\2\2\u012c\u012d"+
		"\7%\2\2\u012d\u012e\5\34\17\2\u012e\u012f\7\"\2\2\u012f\u0130\5\34\17"+
		"\2\u0130\u0131\7\67\2\2\u0131\u0162\3\2\2\2\u0132\u0133\7\66\2\2\u0133"+
		"\u0134\7%\2\2\u0134\u0135\5\34\17\2\u0135\u0136\7\67\2\2\u0136\u0162\3"+
		"\2\2\2\u0137\u0138\7\66\2\2\u0138\u0139\7%\2\2\u0139\u013a\7\"\2\2\u013a"+
		"\u013b\5\34\17\2\u013b\u013c\7\67\2\2\u013c\u0162\3\2\2\2\u013d\u013e"+
		"\7\66\2\2\u013e\u013f\7\'\2\2\u013f\u0140\7h\2\2\u0140\u0141\7i\2\2\u0141"+
		"\u0142\7;\2\2\u0142\u0143\7m\2\2\u0143\u0144\7n\2\2\u0144\u0145\7[\2\2"+
		"\u0145\u0146\7l\2\2\u0146\u0147\7n\2\2\u0147\u0148\7;\2\2\u0148\u0149"+
		"\7i\2\2\u0149\u014a\7j\2\2\u014a\u014b\7n\2\2\u014b\u0162\7\67\2\2\u014c"+
		"\u014d\7\66\2\2\u014d\u014e\7\'\2\2\u014e\u014f\7o\2\2\u014f\u0150\7n"+
		"\2\2\u0150\u0151\7`\2\2\u0151\u0152\7|\2\2\u0152\u0162\7\67\2\2\u0153"+
		"\u0154\7\66\2\2\u0154\u0155\7\'\2\2\u0155\u0156\7o\2\2\u0156\u0157\7n"+
		"\2\2\u0157\u0158\7`\2\2\u0158\u0159\7u\2\2\u0159\u015a\7z\2\2\u015a\u0162"+
		"\7\67\2\2\u015b\u015c\7\66\2\2\u015c\u015d\7\'\2\2\u015d\u015e\7o\2\2"+
		"\u015e\u015f\7]\2\2\u015f\u0160\7j\2\2\u0160\u0162\7\67\2\2\u0161\u012b"+
		"\3\2\2\2\u0161\u0132\3\2\2\2\u0161\u0137\3\2\2\2\u0161\u013d\3\2\2\2\u0161"+
		"\u014c\3\2\2\2\u0161\u0153\3\2\2\2\u0161\u015b\3\2\2\2\u0162\33\3\2\2"+
		"\2\u0163\u0165\5\36\20\2\u0164\u0163\3\2\2\2\u0165\u0166\3\2\2\2\u0166"+
		"\u0164\3\2\2\2\u0166\u0167\3\2\2\2\u0167\35\3\2\2\2\u0168\u0169\t\2\2"+
		"\2\u0169\37\3\2\2\2\u016a\u016b\7\66\2\2\u016b\u016c\7%\2\2\u016c\u016d"+
		"\7>\2\2\u016d\u016e\5\4\3\2\u016e\u016f\7\67\2\2\u016f\u0185\3\2\2\2\u0170"+
		"\u0171\7\66\2\2\u0171\u0172\7%\2\2\u0172\u0173\7?\2\2\u0173\u0174\5\4"+
		"\3\2\u0174\u0175\7\67\2\2\u0175\u0185\3\2\2\2\u0176\u0177\7\66\2\2\u0177"+
		"\u0178\7%\2\2\u0178\u0179\78\2\2\u0179\u017a\7>\2\2\u017a\u017b\5\4\3"+
		"\2\u017b\u017c\7\67\2\2\u017c\u0185\3\2\2\2\u017d\u017e\7\66\2\2\u017e"+
		"\u017f\7%\2\2\u017f\u0180\78\2\2\u0180\u0181\7?\2\2\u0181\u0182\5\4\3"+
		"\2\u0182\u0183\7\67\2\2\u0183\u0185\3\2\2\2\u0184\u016a\3\2\2\2\u0184"+
		"\u0170\3\2\2\2\u0184\u0176\3\2\2\2\u0184\u017d\3\2\2\2\u0185!\3\2\2\2"+
		"\u0186\u0187\7\66\2\2\u0187\u0188\7%\2\2\u0188\u0189\7l\2\2\u0189\u01d5"+
		"\7\67\2\2\u018a\u018b\7\66\2\2\u018b\u018c\7%\2\2\u018c\u018d\58\35\2"+
		"\u018d\u018e\7\67\2\2\u018e\u01d5\3\2\2\2\u018f\u0190\7\66\2\2\u0190\u0191"+
		"\7%\2\2\u0191\u0192\7&\2\2\u0192\u0193\58\35\2\u0193\u0194\7\67\2\2\u0194"+
		"\u01d5\3\2\2\2\u0195\u0196\7\66\2\2\u0196\u0197\7%\2\2\u0197\u0198\7\""+
		"\2\2\u0198\u0199\58\35\2\u0199\u019a\7\67\2\2\u019a\u01d5\3\2\2\2\u019b"+
		"\u019c\7\66\2\2\u019c\u019d\7%\2\2\u019d\u019e\7@\2\2\u019e\u019f\5B\""+
		"\2\u019f\u01a0\7\67\2\2\u01a0\u01d5\3\2\2\2\u01a1\u01a2\7\66\2\2\u01a2"+
		"\u01a3\7%\2\2\u01a3\u01a4\7j\2\2\u01a4\u01a5\79\2\2\u01a5\u01a6\5B\"\2"+
		"\u01a6\u01a7\7\67\2\2\u01a7\u01d5\3\2\2\2\u01a8\u01a9\7\63\2\2\u01a9\u01aa"+
		"\78\2\2\u01aa\u01ab\5B\"\2\u01ab\u01ac\79\2\2\u01ac\u01d5\3\2\2\2\u01ad"+
		"\u01ae\7\63\2\2\u01ae\u01af\7:\2\2\u01af\u01b0\5B\"\2\u01b0\u01b1\7:\2"+
		"\2\u01b1\u01d5\3\2\2\2\u01b2\u01b3\7\63\2\2\u01b3\u01b4\78\2\2\u01b4\u01b5"+
		"\58\35\2\u01b5\u01b6\79\2\2\u01b6\u01d5\3\2\2\2\u01b7\u01b8\7\63\2\2\u01b8"+
		"\u01b9\7:\2\2\u01b9\u01ba\58\35\2\u01ba\u01bb\7:\2\2\u01bb\u01d5\3\2\2"+
		"\2\u01bc\u01bd\7\63\2\2\u01bd\u01be\78\2\2\u01be\u01bf\7&\2\2\u01bf\u01c0"+
		"\58\35\2\u01c0\u01c1\79\2\2\u01c1\u01d5\3\2\2\2\u01c2\u01c3\7\63\2\2\u01c3"+
		"\u01c4\7:\2\2\u01c4\u01c5\7&\2\2\u01c5\u01c6\58\35\2\u01c6\u01c7\7:\2"+
		"\2\u01c7\u01d5\3\2\2\2\u01c8\u01c9\7\63\2\2\u01c9\u01ca\78\2\2\u01ca\u01cb"+
		"\7\"\2\2\u01cb\u01cc\58\35\2\u01cc\u01cd\79\2\2\u01cd\u01d5\3\2\2\2\u01ce"+
		"\u01cf\7\63\2\2\u01cf\u01d0\7:\2\2\u01d0\u01d1\7\"\2\2\u01d1\u01d2\58"+
		"\35\2\u01d2\u01d3\7:\2\2\u01d3\u01d5\3\2\2\2\u01d4\u0186\3\2\2\2\u01d4"+
		"\u018a\3\2\2\2\u01d4\u018f\3\2\2\2\u01d4\u0195\3\2\2\2\u01d4\u019b\3\2"+
		"\2\2\u01d4\u01a1\3\2\2\2\u01d4\u01a8\3\2\2\2\u01d4\u01ad\3\2\2\2\u01d4"+
		"\u01b2\3\2\2\2\u01d4\u01b7\3\2\2\2\u01d4\u01bc\3\2\2\2\u01d4\u01c2\3\2"+
		"\2\2\u01d4\u01c8\3\2\2\2\u01d4\u01ce\3\2\2\2\u01d5#\3\2\2\2\u01d6\u01d7"+
		"\7\66\2\2\u01d7\u01d8\7%\2\2\u01d8\u01d9\7\66\2\2\u01d9\u01da\58\35\2"+
		"\u01da\u01db\7\67\2\2\u01db\u01de\5\4\3\2\u01dc\u01dd\7\65\2\2\u01dd\u01df"+
		"\5\4\3\2\u01de\u01dc\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0"+
		"\u01e1\7\67\2\2\u01e1\u026e\3\2\2\2\u01e2\u01e3\7\66\2\2\u01e3\u01e4\7"+
		"%\2\2\u01e4\u01e5\7\66\2\2\u01e5\u01e6\7&\2\2\u01e6\u01e7\58\35\2\u01e7"+
		"\u01e8\7\67\2\2\u01e8\u01eb\5\4\3\2\u01e9\u01ea\7\65\2\2\u01ea\u01ec\5"+
		"\4\3\2\u01eb\u01e9\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed"+
		"\u01ee\7\67\2\2\u01ee\u026e\3\2\2\2\u01ef\u01f0\7\66\2\2\u01f0\u01f1\7"+
		"%\2\2\u01f1\u01f2\7\66\2\2\u01f2\u01f3\7\"\2\2\u01f3\u01f4\58\35\2\u01f4"+
		"\u01f5\7\67\2\2\u01f5\u01f8\5\4\3\2\u01f6\u01f7\7\65\2\2\u01f7\u01f9\5"+
		"\4\3\2\u01f8\u01f6\3\2\2\2\u01f8\u01f9\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa"+
		"\u01fb\7\67\2\2\u01fb\u026e\3\2\2\2\u01fc\u01fd\7\66\2\2\u01fd\u01fe\7"+
		"%\2\2\u01fe\u01ff\7\66\2\2\u01ff\u0200\78\2\2\u0200\u0201\5B\"\2\u0201"+
		"\u0202\79\2\2\u0202\u0203\7\67\2\2\u0203\u0206\5\4\3\2\u0204\u0205\7\65"+
		"\2\2\u0205\u0207\5\4\3\2\u0206\u0204\3\2\2\2\u0206\u0207\3\2\2\2\u0207"+
		"\u0208\3\2\2\2\u0208\u0209\7\67\2\2\u0209\u026e\3\2\2\2\u020a\u020b\7"+
		"\66\2\2\u020b\u020c\7%\2\2\u020c\u020d\7\66\2\2\u020d\u020e\7:\2\2\u020e"+
		"\u020f\5B\"\2\u020f\u0210\7:\2\2\u0210\u0211\7\67\2\2\u0211\u0214\5\4"+
		"\3\2\u0212\u0213\7\65\2\2\u0213\u0215\5\4\3\2\u0214\u0212\3\2\2\2\u0214"+
		"\u0215\3\2\2\2\u0215\u0216\3\2\2\2\u0216\u0217\7\67\2\2\u0217\u026e\3"+
		"\2\2\2\u0218\u0219\7\66\2\2\u0219\u021a\7%\2\2\u021a\u021b\7\66\2\2\u021b"+
		"\u021c\7l\2\2\u021c\u021d\58\35\2\u021d\u021e\7\67\2\2\u021e\u0221\5\4"+
		"\3\2\u021f\u0220\7\65\2\2\u0220\u0222\5\4\3\2\u0221\u021f\3\2\2\2\u0221"+
		"\u0222\3\2\2\2\u0222\u0223\3\2\2\2\u0223\u0224\7\67\2\2\u0224\u026e\3"+
		"\2\2\2\u0225\u0226\7\66\2\2\u0226\u0227\7%\2\2\u0227\u0228\7\66\2\2\u0228"+
		"\u0229\7l\2\2\u0229\u022a\7\67\2\2\u022a\u022d\5\4\3\2\u022b\u022c\7\65"+
		"\2\2\u022c\u022e\5\4\3\2\u022d\u022b\3\2\2\2\u022d\u022e\3\2\2\2\u022e"+
		"\u022f\3\2\2\2\u022f\u0230\7\67\2\2\u0230\u026e\3\2\2\2\u0231\u0232\7"+
		"\66\2\2\u0232\u0233\7%\2\2\u0233\u0234\7\66\2\2\u0234\u0235\7l\2\2\u0235"+
		"\u0236\7@\2\2\u0236\u0237\5B\"\2\u0237\u0238\7\67\2\2\u0238\u023b\5\4"+
		"\3\2\u0239\u023a\7\65\2\2\u023a\u023c\5\4\3\2\u023b\u0239\3\2\2\2\u023b"+
		"\u023c\3\2\2\2\u023c\u023d\3\2\2\2\u023d\u023e\7\67\2\2\u023e\u026e\3"+
		"\2\2\2\u023f\u0240\7\66\2\2\u0240\u0241\7%\2\2\u0241\u0242\7\66\2\2\u0242"+
		"\u0243\7^\2\2\u0243\u0244\7_\2\2\u0244\u0245\7`\2\2\u0245\u0246\7c\2\2"+
		"\u0246\u0247\7h\2\2\u0247\u0248\7_\2\2\u0248\u0249\7\67\2\2\u0249\u024c"+
		"\5\4\3\2\u024a\u024b\7\65\2\2\u024b\u024d\5\4\3\2\u024c\u024a\3\2\2\2"+
		"\u024c\u024d\3\2\2\2\u024d\u024e\3\2\2\2\u024e\u024f\7\67\2\2\u024f\u026e"+
		"\3\2\2\2\u0250\u0251\7\66\2\2\u0251\u0252\7%\2\2\u0252\u0253\7\66\2\2"+
		"\u0253\u0254\7A\2\2\u0254\u0255\7S\2\2\u0255\u0256\7S\2\2\u0256\u0257"+
		"\7E\2\2\u0257\u0258\7R\2\2\u0258\u0259\7T\2\2\u0259\u025a\7\67\2\2\u025a"+
		"\u025d\5\4\3\2\u025b\u025c\7\65\2\2\u025c\u025e\5\4\3\2\u025d\u025b\3"+
		"\2\2\2\u025d\u025e\3\2\2\2\u025e\u025f\3\2\2\2\u025f\u0260\7\67\2\2\u0260"+
		"\u026e\3\2\2\2\u0261\u0262\7\66\2\2\u0262\u0263\7%\2\2\u0263\u0264\7\66"+
		"\2\2\u0264\u0265\5B\"\2\u0265\u0266\7\67\2\2\u0266\u0269\5\4\3\2\u0267"+
		"\u0268\7\65\2\2\u0268\u026a\5\4\3\2\u0269\u0267\3\2\2\2\u0269\u026a\3"+
		"\2\2\2\u026a\u026b\3\2\2\2\u026b\u026c\7\67\2\2\u026c\u026e\3\2\2\2\u026d"+
		"\u01d6\3\2\2\2\u026d\u01e2\3\2\2\2\u026d\u01ef\3\2\2\2\u026d\u01fc\3\2"+
		"\2\2\u026d\u020a\3\2\2\2\u026d\u0218\3\2\2\2\u026d\u0225\3\2\2\2\u026d"+
		"\u0231\3\2\2\2\u026d\u023f\3\2\2\2\u026d\u0250\3\2\2\2\u026d\u0261\3\2"+
		"\2\2\u026e%\3\2\2\2\u026f\u0270\7\66\2\2\u0270\u0271\7\'\2\2\u0271\u0272"+
		"\7[\2\2\u0272\u0273\7]\2\2\u0273\u0274\7]\2\2\u0274\u0275\7_\2\2\u0275"+
		"\u0276\7j\2\2\u0276\u0277\7n\2\2\u0277\u02d4\7\67\2\2\u0278\u0279\7\66"+
		"\2\2\u0279\u027a\7\'\2\2\u027a\u027e\7`\2\2\u027b\u027c\7[\2\2\u027c\u027d"+
		"\7c\2\2\u027d\u027f\7f\2\2\u027e\u027b\3\2\2\2\u027e\u027f\3\2\2\2\u027f"+
		"\u0280\3\2\2\2\u0280\u02d4\7\67\2\2\u0281\u0282\7\66\2\2\u0282\u0287\7"+
		"\'\2\2\u0283\u0284\7g\2\2\u0284\u0285\7[\2\2\u0285\u0286\7l\2\2\u0286"+
		"\u0288\7e\2\2\u0287\u0283\3\2\2\2\u0287\u0288\3\2\2\2\u0288\u0289\3\2"+
		"\2\2\u0289\u028a\7<\2\2\u028a\u028b\7h\2\2\u028b\u028c\7[\2\2\u028c\u028d"+
		"\7g\2\2\u028d\u028e\7_\2\2\u028e\u02d4\7\67\2\2\u028f\u0290\7\66\2\2\u0290"+
		"\u0291\7\'\2\2\u0291\u0292\7]\2\2\u0292\u0293\7i\2\2\u0293\u0294\7g\2"+
		"\2\u0294\u0295\7g\2\2\u0295\u0296\7c\2\2\u0296\u0297\7n\2\2\u0297\u02d4"+
		"\7\67\2\2\u0298\u0299\7\66\2\2\u0299\u029a\7\'\2\2\u029a\u029b\7j\2\2"+
		"\u029b\u029c\7l\2\2\u029c\u029d\7o\2\2\u029d\u029e\7h\2\2\u029e\u029f"+
		"\7_\2\2\u029f\u02d4\7\67\2\2\u02a0\u02a1\7\66\2\2\u02a1\u02a2\7\'\2\2"+
		"\u02a2\u02a3\7j\2\2\u02a3\u02a4\7l\2\2\u02a4\u02a5\7o\2\2\u02a5\u02a6"+
		"\7h\2\2\u02a6\u02a7\7_\2\2\u02a7\u02a8\7<\2\2\u02a8\u02a9\7h\2\2\u02a9"+
		"\u02aa\7[\2\2\u02aa\u02ab\7g\2\2\u02ab\u02ac\7_\2\2\u02ac\u02d4\7\67\2"+
		"\2\u02ad\u02ae\7\66\2\2\u02ae\u02af\7\'\2\2\u02af\u02b0\7m\2\2\u02b0\u02b1"+
		"\7e\2\2\u02b1\u02b2\7c\2\2\u02b2\u02b3\7j\2\2\u02b3\u02d4\7\67\2\2\u02b4"+
		"\u02b5\7\66\2\2\u02b5\u02b6\7\'\2\2\u02b6\u02b7\7m\2\2\u02b7\u02b8\7e"+
		"\2\2\u02b8\u02b9\7c\2\2\u02b9\u02ba\7j\2\2\u02ba\u02bb\7<\2\2\u02bb\u02bc"+
		"\7h\2\2\u02bc\u02bd\7[\2\2\u02bd\u02be\7g\2\2\u02be\u02bf\7_\2\2\u02bf"+
		"\u02d4\7\67\2\2\u02c0\u02c1\7\66\2\2\u02c1\u02c2\7\'\2\2\u02c2\u02c3\7"+
		"n\2\2\u02c3\u02c4\7b\2\2\u02c4\u02c5\7_\2\2\u02c5\u02c6\7h\2\2\u02c6\u02d4"+
		"\7\67\2\2\u02c7\u02c8\7\66\2\2\u02c8\u02c9\7\'\2\2\u02c9\u02ca\7n\2\2"+
		"\u02ca\u02cb\7b\2\2\u02cb\u02cc\7_\2\2\u02cc\u02cd\7h\2\2\u02cd\u02ce"+
		"\7<\2\2\u02ce\u02cf\7h\2\2\u02cf\u02d0\7[\2\2\u02d0\u02d1\7g\2\2\u02d1"+
		"\u02d2\7_\2\2\u02d2\u02d4\7\67\2\2\u02d3\u026f\3\2\2\2\u02d3\u0278\3\2"+
		"\2\2\u02d3\u0281\3\2\2\2\u02d3\u028f\3\2\2\2\u02d3\u0298\3\2\2\2\u02d3"+
		"\u02a0\3\2\2\2\u02d3\u02ad\3\2\2\2\u02d3\u02b4\3\2\2\2\u02d3\u02c0\3\2"+
		"\2\2\u02d3\u02c7\3\2\2\2\u02d4\'\3\2\2\2\u02d5\u02d6\7\66\2\2\u02d6\u02d7"+
		"\7\'\2\2\u02d7\u02d8\7]\2\2\u02d8\u02d9\7l\2\2\u02d9\u0313\7\67\2\2\u02da"+
		"\u02db\7\66\2\2\u02db\u02dc\7\'\2\2\u02dc\u02dd\7f\2\2\u02dd\u02de\7`"+
		"\2\2\u02de\u0313\7\67\2\2\u02df\u02e0\7\66\2\2\u02e0\u02e1\7\'\2\2\u02e1"+
		"\u02e2\7]\2\2\u02e2\u02e3\7l\2\2\u02e3\u02e4\7f\2\2\u02e4\u02e5\7`\2\2"+
		"\u02e5\u0313\7\67\2\2\u02e6\u02e7\7\66\2\2\u02e7\u02e8\7\'\2\2\u02e8\u02e9"+
		"\7[\2\2\u02e9\u02ea\7h\2\2\u02ea\u02eb\7s\2\2\u02eb\u02ec\7]\2\2\u02ec"+
		"\u02ed\7l\2\2\u02ed\u02ee\7f\2\2\u02ee\u02ef\7`\2\2\u02ef\u0313\7\67\2"+
		"\2\u02f0\u02f1\7\66\2\2\u02f1\u02f2\7\'\2\2\u02f2\u02f3\7[\2\2\u02f3\u02f4"+
		"\7h\2\2\u02f4\u02f5\7s\2\2\u02f5\u0313\7\67\2\2\u02f6\u02f7\7\66\2\2\u02f7"+
		"\u02f8\7\'\2\2\u02f8\u02f9\7\\\2\2\u02f9\u02fa\7m\2\2\u02fa\u02fb\7l\2"+
		"\2\u02fb\u02fc\7;\2\2\u02fc\u02fd\7[\2\2\u02fd\u02fe\7h\2\2\u02fe\u02ff"+
		"\7s\2\2\u02ff\u0300\7]\2\2\u0300\u0301\7l\2\2\u0301\u0302\7f\2\2\u0302"+
		"\u0303\7`\2\2\u0303\u0313\7\67\2\2\u0304\u0305\7\66\2\2\u0305\u0306\7"+
		"\'\2\2\u0306\u0307\7\\\2\2\u0307\u0308\7m\2\2\u0308\u0309\7l\2\2\u0309"+
		"\u030a\7;\2\2\u030a\u030b\7o\2\2\u030b\u030c\7h\2\2\u030c\u030d\7c\2\2"+
		"\u030d\u030e\7]\2\2\u030e\u030f\7i\2\2\u030f\u0310\7^\2\2\u0310\u0311"+
		"\7_\2\2\u0311\u0313\7\67\2\2\u0312\u02d5\3\2\2\2\u0312\u02da\3\2\2\2\u0312"+
		"\u02df\3\2\2\2\u0312\u02e6\3\2\2\2\u0312\u02f0\3\2\2\2\u0312\u02f6\3\2"+
		"\2\2\u0312\u0304\3\2\2\2\u0313)\3\2\2\2\u0314\u0315\7\66\2\2\u0315\u0316"+
		"\7%\2\2\u0316\u0317\7]\2\2\u0317\u031f\7\67\2\2\u0318\u0319\7\66\2\2\u0319"+
		"\u031a\7%\2\2\u031a\u031b\7]\2\2\u031b\u031c\58\35\2\u031c\u031d\7\67"+
		"\2\2\u031d\u031f\3\2\2\2\u031e\u0314\3\2\2\2\u031e\u0318\3\2\2\2\u031f"+
		"+\3\2\2\2\u0320\u033b\5\"\22\2\u0321\u033b\5\60\31\2\u0322\u033b\5\62"+
		"\32\2\u0323\u033b\5\16\b\2\u0324\u033b\5\24\13\2\u0325\u033b\5\26\f\2"+
		"\u0326\u033b\5\30\r\2\u0327\u033b\5\32\16\2\u0328\u033b\5 \21\2\u0329"+
		"\u033b\5\20\t\2\u032a\u033b\5$\23\2\u032b\u033b\5&\24\2\u032c\u033b\5"+
		"(\25\2\u032d\u033b\5*\26\2\u032e\u033b\7\16\2\2\u032f\u033b\7!\2\2\u0330"+
		"\u033b\7-\2\2\u0331\u033b\7+\2\2\u0332\u033b\7,\2\2\u0333\u033b\7.\2\2"+
		"\u0334\u033b\7/\2\2\u0335\u033b\7\60\2\2\u0336\u033b\7\61\2\2\u0337\u033b"+
		"\7\62\2\2\u0338\u033b\7\17\2\2\u0339\u033b\7\36\2\2\u033a\u0320\3\2\2"+
		"\2\u033a\u0321\3\2\2\2\u033a\u0322\3\2\2\2\u033a\u0323\3\2\2\2\u033a\u0324"+
		"\3\2\2\2\u033a\u0325\3\2\2\2\u033a\u0326\3\2\2\2\u033a\u0327\3\2\2\2\u033a"+
		"\u0328\3\2\2\2\u033a\u0329\3\2\2\2\u033a\u032a\3\2\2\2\u033a\u032b\3\2"+
		"\2\2\u033a\u032c\3\2\2\2\u033a\u032d\3\2\2\2\u033a\u032e\3\2\2\2\u033a"+
		"\u032f\3\2\2\2\u033a\u0330\3\2\2\2\u033a\u0331\3\2\2\2\u033a\u0332\3\2"+
		"\2\2\u033a\u0333\3\2\2\2\u033a\u0334\3\2\2\2\u033a\u0335\3\2\2\2\u033a"+
		"\u0336\3\2\2\2\u033a\u0337\3\2\2\2\u033a\u0338\3\2\2\2\u033a\u0339\3\2"+
		"\2\2\u033b-\3\2\2\2\u033c\u033d\5\64\33\2\u033d\u033e\7\"\2\2\u033e\u033f"+
		"\5\64\33\2\u033f\u0344\3\2\2\2\u0340\u0344\5\60\31\2\u0341\u0344\5\64"+
		"\33\2\u0342\u0344\5\22\n\2\u0343\u033c\3\2\2\2\u0343\u0340\3\2\2\2\u0343"+
		"\u0341\3\2\2\2\u0343\u0342\3\2\2\2\u0344/\3\2\2\2\u0345\u0359\7#\2\2\u0346"+
		"\u0359\7$\2\2\u0347\u0359\7\6\2\2\u0348\u0359\7\20\2\2\u0349\u0359\7\21"+
		"\2\2\u034a\u0359\7\22\2\2\u034b\u0359\7\23\2\2\u034c\u0359\7\24\2\2\u034d"+
		"\u0359\7\25\2\2\u034e\u0359\7\26\2\2\u034f\u0359\7\27\2\2\u0350\u0359"+
		"\7\30\2\2\u0351\u0359\7\31\2\2\u0352\u0359\7\32\2\2\u0353\u0359\7\33\2"+
		"\2\u0354\u0359\7\34\2\2\u0355\u0359\7\35\2\2\u0356\u0357\7\f\2\2\u0357"+
		"\u0359\13\2\2\2\u0358\u0345\3\2\2\2\u0358\u0346\3\2\2\2\u0358\u0347\3"+
		"\2\2\2\u0358\u0348\3\2\2\2\u0358\u0349\3\2\2\2\u0358\u034a\3\2\2\2\u0358"+
		"\u034b\3\2\2\2\u0358\u034c\3\2\2\2\u0358\u034d\3\2\2\2\u0358\u034e\3\2"+
		"\2\2\u0358\u034f\3\2\2\2\u0358\u0350\3\2\2\2\u0358\u0351\3\2\2\2\u0358"+
		"\u0352\3\2\2\2\u0358\u0353\3\2\2\2\u0358\u0354\3\2\2\2\u0358\u0355\3\2"+
		"\2\2\u0358\u0356\3\2\2\2\u0359\61\3\2\2\2\u035a\u035d\5\66\34\2\u035b"+
		"\u035d\7 \2\2\u035c\u035a\3\2\2\2\u035c\u035b\3\2\2\2\u035d\63\3\2\2\2"+
		"\u035e\u036b\5\66\34\2\u035f\u036b\7\16\2\2\u0360\u036b\7\37\2\2\u0361"+
		"\u036b\7!\2\2\u0362\u036b\7%\2\2\u0363\u036b\7&\2\2\u0364\u036b\7\'\2"+
		"\2\u0365\u036b\7+\2\2\u0366\u036b\7.\2\2\u0367\u036b\7\65\2\2\u0368\u036b"+
		"\7\66\2\2\u0369\u036b\7\67\2\2\u036a\u035e\3\2\2\2\u036a\u035f\3\2\2\2"+
		"\u036a\u0360\3\2\2\2\u036a\u0361\3\2\2\2\u036a\u0362\3\2\2\2\u036a\u0363"+
		"\3\2\2\2\u036a\u0364\3\2\2\2\u036a\u0365\3\2\2\2\u036a\u0366\3\2\2\2\u036a"+
		"\u0367\3\2\2\2\u036a\u0368\3\2\2\2\u036a\u0369\3\2\2\2\u036b\65\3\2\2"+
		"\2\u036c\u0387\5:\36\2\u036d\u0387\5J&\2\u036e\u0387\5@!\2\u036f\u0387"+
		"\7\5\2\2\u0370\u0387\7\7\2\2\u0371\u0387\7\b\2\2\u0372\u0387\7\t\2\2\u0373"+
		"\u0387\7\n\2\2\u0374\u0387\7\13\2\2\u0375\u0387\7\r\2\2\u0376\u0387\7"+
		"\3\2\2\u0377\u0387\7\4\2\2\u0378\u0387\7(\2\2\u0379\u0387\7)\2\2\u037a"+
		"\u0387\7*\2\2\u037b\u0387\7\"\2\2\u037c\u0387\78\2\2\u037d\u0387\79\2"+
		"\2\u037e\u0387\7:\2\2\u037f\u0387\7;\2\2\u0380\u0387\7<\2\2\u0381\u0387"+
		"\7=\2\2\u0382\u0387\7>\2\2\u0383\u0387\7?\2\2\u0384\u0387\7@\2\2\u0385"+
		"\u0387\7\177\2\2\u0386\u036c\3\2\2\2\u0386\u036d\3\2\2\2\u0386\u036e\3"+
		"\2\2\2\u0386\u036f\3\2\2\2\u0386\u0370\3\2\2\2\u0386\u0371\3\2\2\2\u0386"+
		"\u0372\3\2\2\2\u0386\u0373\3\2\2\2\u0386\u0374\3\2\2\2\u0386\u0375\3\2"+
		"\2\2\u0386\u0376\3\2\2\2\u0386\u0377\3\2\2\2\u0386\u0378\3\2\2\2\u0386"+
		"\u0379\3\2\2\2\u0386\u037a\3\2\2\2\u0386\u037b\3\2\2\2\u0386\u037c\3\2"+
		"\2\2\u0386\u037d\3\2\2\2\u0386\u037e\3\2\2\2\u0386\u037f\3\2\2\2\u0386"+
		"\u0380\3\2\2\2\u0386\u0381\3\2\2\2\u0386\u0382\3\2\2\2\u0386\u0383\3\2"+
		"\2\2\u0386\u0384\3\2\2\2\u0386\u0385\3\2\2\2\u0387\67\3\2\2\2\u0388\u0389"+
		"\5> \2\u03899\3\2\2\2\u038a\u038b\7\f\2\2\u038b\u038c\t\3\2\2\u038c\u038d"+
		"\5<\37\2\u038d\u038e\5<\37\2\u038e\u0394\3\2\2\2\u038f\u0390\7\f\2\2\u0390"+
		"\u0391\5<\37\2\u0391\u0392\5<\37\2\u0392\u0394\3\2\2\2\u0393\u038a\3\2"+
		"\2\2\u0393\u038f\3\2\2\2\u0394;\3\2\2\2\u0395\u0396\t\4\2\2\u0396=\3\2"+
		"\2\2\u0397\u0399\5@!\2\u0398\u0397\3\2\2\2\u0399\u039a\3\2\2\2\u039a\u0398"+
		"\3\2\2\2\u039a\u039b\3\2\2\2\u039b?\3\2\2\2\u039c\u039d\t\5\2\2\u039d"+
		"A\3\2\2\2\u039e\u039f\5D#\2\u039fC\3\2\2\2\u03a0\u03a3\5J&\2\u03a1\u03a3"+
		"\7;\2\2\u03a2\u03a0\3\2\2\2\u03a2\u03a1\3\2\2\2\u03a3\u03a9\3\2\2\2\u03a4"+
		"\u03a8\5J&\2\u03a5\u03a8\7;\2\2\u03a6\u03a8\5@!\2\u03a7\u03a4\3\2\2\2"+
		"\u03a7\u03a5\3\2\2\2\u03a7\u03a6\3\2\2\2\u03a8\u03ab\3\2\2\2\u03a9\u03a7"+
		"\3\2\2\2\u03a9\u03aa\3\2\2\2\u03aaE\3\2\2\2\u03ab\u03a9\3\2\2\2\u03ac"+
		"\u03ae\5H%\2\u03ad\u03ac\3\2\2\2\u03ae\u03af\3\2\2\2\u03af\u03ad\3\2\2"+
		"\2\u03af\u03b0\3\2\2\2\u03b0G\3\2\2\2\u03b1\u03b2\n\6\2\2\u03b2I\3\2\2"+
		"\2\u03b3\u03b4\t\7\2\2\u03b4K\3\2\2\2\63TZ_y~\u0087\u0091\u009a\u00a4"+
		"\u00ad\u00b5\u00b9\u00e4\u00e9\u0108\u0123\u0161\u0166\u0184\u01d4\u01de"+
		"\u01eb\u01f8\u0206\u0214\u0221\u022d\u023b\u024c\u025d\u0269\u026d\u027e"+
		"\u0287\u02d3\u0312\u031e\u033a\u0343\u0358\u035c\u036a\u0386\u0393\u039a"+
		"\u03a2\u03a7\u03a9\u03af";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}