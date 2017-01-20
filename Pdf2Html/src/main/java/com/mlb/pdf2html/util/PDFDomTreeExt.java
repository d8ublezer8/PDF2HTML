package com.mlb.pdf2html.util;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.fit.pdfdom.PathSegment;
import org.fit.pdfdom.TextMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class PDFDomTreeExt extends PDFDomTree {
	private static Logger log = LoggerFactory.getLogger(PDFDomTreeExt.class);

	private String preTextTop = null;
	private String preTextLeft = null;
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
		if (preTextTop != null && preTextTop.equals(topInfo[0])) {
			el = preTextEl;
		} else {
			if (preTextTop != null && !preTextTop.equals(topInfo[0])) {
				String styleInfo[] = style.split(";");
				style="";
				for(int i=0;i<styleInfo.length;i++){
					if(i==0){
						style+=preTextTop+";";
					}else if(i==1){
						style+=preTextLeft+";";
					}else{
						style+=styleInfo[i];
					}
				}
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
			preTextLeft = topInfo[1];
		}
		lineWidth += width;
		return el;
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

	/*@Override
	protected void processOperator(Operator operator, List<COSBase> arguments) throws IOException {
		String operation = operator.getName();
		String cosStr = "";
		if (arguments != null) {
			for (COSBase cos : arguments) {
				cosStr += cos.toString();
			}
			System.out.println("Operator: " + operation + ":" + arguments.size() + " => " + cosStr);
		}else{
			System.out.println("Operator: " + operation);
		}
		// word spacing
		if (operation.equals("Tw")) {
			style.setWordSpacing(getLength(arguments.get(0)));
		}

		// letter spacing
		else if (operation.equals("Tc")) {
			style.setLetterSpacing(getLength(arguments.get(0)));
		}

		// graphics
		else if (operation.equals("m")) // move
		{
			if (!disableGraphics) {
				if (arguments.size() == 2) {
					float[] pos = transformPosition(getLength(arguments.get(0)), getLength(arguments.get(1)));
					path_x = pos[0];
					path_y = pos[1];
					path_start_x = pos[0];
					path_start_y = pos[1];
				}
			}
		} else if (operation.equals("l")) // line
		{
			if (!disableGraphics) {
				if (arguments.size() == 2) {
					float[] pos = transformPosition(getLength(arguments.get(0)), getLength(arguments.get(1)));
					graphicsPath.add(new PathSegment(path_x, path_y, pos[0], pos[1]));
					path_x = pos[0];
					path_y = pos[1];
				}
			}
		} else if (operation.equals("h")) // end subpath
		{
			if (!disableGraphics) {
				graphicsPath.add(new PathSegment(path_x, path_y, path_start_x, path_start_y));
			}
		}

		// rectangle
		else if (operation.equals("re")) {
			if (!disableGraphics) {
				if (arguments.size() == 4) {
					float x = getLength(arguments.get(0));
					float y = getLength(arguments.get(1));
					float width = getLength(arguments.get(2));
					float height = getLength(arguments.get(3));

					float[] p1 = transformPosition(x, y);
					float[] p2 = transformPosition(x + width, y + height);

					graphicsPath.add(new PathSegment(p1[0], p1[1], p2[0], p1[1]));
					graphicsPath.add(new PathSegment(p2[0], p1[1], p2[0], p2[1]));
					graphicsPath.add(new PathSegment(p2[0], p2[1], p1[0], p2[1]));
					graphicsPath.add(new PathSegment(p1[0], p2[1], p1[0], p1[1]));
				}
			}
		}

		// fill
		else if (operation.equals("f") || operation.equals("F") || operation.equals("f*")) {
			renderPath(graphicsPath, false, true);
			graphicsPath.removeAllElements();
		}

		// stroke
		else if (operation.equals("S")) {
			renderPath(graphicsPath, true, false);
			graphicsPath.removeAllElements();
		} else if (operation.equals("s")) {
			graphicsPath.add(new PathSegment(path_x, path_y, path_start_x, path_start_y));
			renderPath(graphicsPath, true, false);
			graphicsPath.removeAllElements();
		}

		// stroke and fill
		else if (operation.equals("B") || operation.equals("B*")) {
			renderPath(graphicsPath, true, true);
			graphicsPath.removeAllElements();
		} else if (operation.equals("b") || operation.equals("b*")) {
			graphicsPath.add(new PathSegment(path_x, path_y, path_start_x, path_start_y));
			renderPath(graphicsPath, true, true);
			graphicsPath.removeAllElements();
		}

		// cancel path
		else if (operation.equals("n")) {
			graphicsPath.removeAllElements();
		}

		// invoke named object - images
		else if (operation.equals("Do")) {
			if (!disableImages)
				processImageOperation(arguments);
		}

		super.processOperator(operator, arguments);
	}
*/
}
