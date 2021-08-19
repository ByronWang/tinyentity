package cn.sj1.tinyentity;

import java.util.Collection;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sj1.tinyhtml.HTMLBuilder;

public class EntitySchemaHtmlPresentation  {
	Logger logger = LoggerFactory.getLogger(EntitySchemaHtmlPresentation.class);

	public StringBuilder displayEntityPage(EntitySchema entity, String prev, String next) {
		HTMLBuilder html = new HTMLBuilder();
		html.append("<style>"
				+ "table { border-collapse: collapse;table-layout: fixed; }"
				+ "caption { text-align:left; background: slategray;}"
				+ "td { padding: 6px; border: 1px solid #ccc; text-align: left;text-overflow: ellipsis; overflow: hidden;white-space: nowrap;}"
				+ "th { background: darkgrey; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;}"
				+ "th a { color: white;}"
				+ "th.good { background: green;}"
				+ "th.knowntype { background: darkseagreen;}"
				+ "th.unknown { background: gray;}"
				+ "th span {display: block;font-size: 50%;color: lightgray;white-space: nowrap;}"
				+ "</style>\n");

		if (prev != null) {
			html.append(String.format("<a href=\"./%1$s\">Prev</a>", prev));
		}

		if (next != null) {
			html.append(String.format("<a href=\"./%1$s\">Next</a>", next));
		}

		displayEntity(html, entity);

		return html.getStringBuilder();
	}

	// BigDecimal BigDecimal Double Float
	private void displayEntity(HTMLBuilder html, EntitySchema entity) {

		displayEntityChild(html, entity);

		displayEntityList(html, entity.getProperties().values());
	}

	private void displayEntityList(HTMLBuilder html, Collection<Property> entitySchema) {
		html.table_();

		displayEntityListHeader(html, entitySchema);

		displayEntityListBody(html, entitySchema);

		html._table();
	}

	private void displayEntityListHeader(HTMLBuilder html, Collection<Property> entitySchema) {
		html.thead_();
		html.tr_();

		html.th("good", 20, "Name");
		html.th("good", 20, "Size");
		html.th("good", 20, "Java Class");
		html.th("good", 20, "Is Key");
		html.th("good", 20, "Is ID");

		html._tr();
		html._thead();

	}

	private void displayEntityListBody(HTMLBuilder html, Collection<Property> entityList) {
		for (Property property : entityList) {

			html.tr_();

			html.td(property.getName());

			html.td((Object) property.getSize());
			if (property.getCls() != null) {
				html.td(property.getCls().getName().replaceAll("java.lang.", ""));
			} else {
				html.td((Consumer<StringBuilder>) null);
			}
			html.td(property.isId());

			html.td(property.isKey());

			html._tr();
		}
	}

	private void displayEntityChild(HTMLBuilder html, EntitySchema entity) {
		html.table_();
		html.tbody_();

//		int i = 0;
//		int span = 3;
//		for (Property field : entitySchema.getProperties().values()) {
//
//			String name = field.getName();
//			String tagClass = "";
//			tagClass = " good";
//
//			String impantance = (String) field.getAttribute("impantance");
//			if ("basic".equals(impantance)) {
//				tagClass = "good";
//			} else if ("relation".equals(impantance)) {
//				tagClass = "unknown";
//			} else if ("ext".equals(impantance)) {
//				tagClass = "unknown";
//			} else {
//
//			}
////
//			int width = name.length() * 2;
//			if (field.getSize() > width) {
//				width = field.getSize();
//
//				int maxValueLength = field.getSize();
//				if (maxValueLength > width) width = maxValueLength;
//			}
//
//			int spanthis = 1;
//			if (width > 40) {
//				width = 40;
//			}
//
//			int minWidth = width * 8;
//
//			int maxValueLength = 0;
//			maxValueLength = field.getSize();
//
//			if (maxValueLength > 40) {
//				maxValueLength = 40;
//				spanthis = span * 2 - 1;
//
//				if (i % span == 0) {
//					i += span;
//					html.append("<tr>\n");
//				} else {
//					html.append("</tr>\n");
//					html.append("<tr>\n");
//					i += span - (i % span) + span;
//				}
//			} else {
//				if (i % span == 0) html.append("<tr>\n");
//				i++;
//			}
//
//			int maxWidth = maxValueLength * 11;
//
//			html.append('<').append("th").append(' ').append("class=\"" + tagClass + "\" style=\"min-width:").append(minWidth).append("px;\"max-width:").append(minWidth).append("px;\"").append('>');
////			if (!isCode) html.append(name);
////			else html.append("<a href=\"/entities/").append(entitySchema.getName()).append("\">").append(name).append("</a>");
//			html.append(name);
////			html.append("<span>").append(datatype).append("<br>").append(remarks).append("</span>");
//			html.append("</").append("th").append('>');
//
//			if (spanthis == 1) {
//				html.append('<').append("td").append(" style=\"max-width:").append(maxWidth).append("px;\"").append('>');
//			} else {
//				html.append('<').append("td").append(" colspan=\"").append(spanthis).append("\"").append(" style=\"max-width:").append(maxWidth).append("px;\"").append('>');
//			}
//
//			Object value = entity.getProperty(field.getName());
//			if (value != null) {
//				html.append(String.valueOf(value));
//			} else {
//				html.append("-");
//			}
//
//			html.append("</").append("td").append('>');
//			if (i % span == 0) html.append("</tr>\n");
//		}
//
//		if (i % span < span) html.append("</tr>\n");
//
////		if (displayData) 
////		displayEntityListData(entityList, html);
		html._tbody();
		html._table();
	}

}
