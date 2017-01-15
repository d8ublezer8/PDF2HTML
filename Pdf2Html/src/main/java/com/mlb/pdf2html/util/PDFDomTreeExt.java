package com.mlb.pdf2html.util;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.fit.pdfdom.TextMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class PDFDomTreeExt extends PDFDomTree {
	private static Logger log = LoggerFactory.getLogger(PDFDomTreeExt.class);

	private String preTextTop = null;
	private Element preTextEl = null;
	private float lineWidth = 0;

	public PDFDomTreeExt() throws IOException, ParserConfigurationException {
		super();
	}

	public PDFDomTreeExt(PDFDomTreeConfig config) throws IOException, ParserConfigurationException {
		super(config);
	}

	@Override
	protected void createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		DocumentType doctype = builder.getDOMImplementation().createDocumentType("html", null, null);
		doc = builder.getDOMImplementation().createDocument("", "html", doctype);

		head = doc.createElement("head");
		Element meta = doc.createElement("meta");
		meta.setAttribute("http-equiv", "content-type");
		meta.setAttribute("content", "text/html;charset=utf-8");
		head.appendChild(meta);
		title = doc.createElement("title");
		title.setTextContent("PDF Document");
		head.appendChild(title);
		globalStyle = doc.createElement("style");
		globalStyle.setAttribute("type", "text/css");
		head.appendChild(globalStyle);

		body = doc.createElement("body");

		Element root = doc.getDocumentElement();
		root.appendChild(head);
		root.appendChild(body);
	}

	@Override
	protected Element createPageElement() {
		String pstyle = "";
		PDRectangle layout = getCurrentMediaBox();
		if (layout != null) {
			/*
			 * System.out.println("x1 " + layout.getLowerLeftX());
			 * System.out.println("y1 " + layout.getLowerLeftY());
			 * System.out.println("x2 " + layout.getUpperRightX());
			 * System.out.println("y2 " + layout.getUpperRightY());
			 * System.out.println("rot " + pdpage.findRotation());
			 */

			float w = layout.getWidth();
			float h = layout.getHeight();
			final int rot = pdpage.getRotation();
			if (rot == 90 || rot == 270) {
				float x = w;
				w = h;
				h = x;
			}

			pstyle = "width:" + w + UNIT + ";" + "height:" + h + UNIT + ";";
			pstyle += "overflow:hidden;";
		} else {
			log.warn("No media box found");
		}
		Element el = doc.createElement("div");
		el.setAttribute("id", "page_" + (pagecnt++));
		el.setAttribute("class", "page");
		el.setAttribute("style", pstyle);
		return el;
	}

	@Override
	protected Element createTextElement(float width) {
		Element el = null;
		String style = curstyle.toString();
		String topInfo[] = style.split(";");
		// 이전줄에 데이터 추가
		if (preTextTop != null && preTextTop.equals(topInfo[0])) {
			el = preTextEl;
		} else {
			// 첫줄 생성
			if (preTextTop != null && !preTextTop.equals(topInfo[0])) {
				style += "width:" + lineWidth + UNIT + ";";
				el = preTextEl;
				el.setAttribute("style", style);
				lineWidth = 0;
			}
			el = doc.createElement("div");
			el.setAttribute("id", "p" + (textcnt++));
			el.setAttribute("class", "p");
			preTextEl = el;
			preTextTop = topInfo[0];
		}
		lineWidth += width;
		return el;
	}

	@Override
	protected void renderText(String data, TextMetrics metrics) {
		curpage.appendChild(createTextElement(data, metrics.getWidth()));
	}

	@Override
	protected void finishBox() {
		if (textLine.length() > 0) {
			String s;
			if (isReversed(Character.getDirectionality(textLine.charAt(0))))
				s = textLine.reverse().toString();
			else
				s = textLine.toString();
			if (s.contains("“")) {
				s = s.replace("“", "\"");
			}
			if (s.contains("”")) {
				s = s.replace("”", "\"");
			}
			curstyle.setLeft(textMetrics.getX());
			curstyle.setTop(textMetrics.getTop());
			curstyle.setLineHeight(textMetrics.getHeight());

			renderText(s, textMetrics);
			textLine = new StringBuilder();
			textMetrics = null;
		}
	}

}
