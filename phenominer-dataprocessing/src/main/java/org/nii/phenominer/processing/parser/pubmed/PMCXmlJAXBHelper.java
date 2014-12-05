package org.nii.phenominer.processing.parser.pubmed;

import java.util.List;

import org.nii.phenominer.processing.data.pubmeddtd.Abstract;
import org.nii.phenominer.processing.data.pubmeddtd.Article;
import org.nii.phenominer.processing.data.pubmeddtd.ArticleId;
import org.nii.phenominer.processing.data.pubmeddtd.Body;
import org.nii.phenominer.processing.data.pubmeddtd.Bold;
import org.nii.phenominer.processing.data.pubmeddtd.Italic;
import org.nii.phenominer.processing.data.pubmeddtd.Monospace;
import org.nii.phenominer.processing.data.pubmeddtd.P;
import org.nii.phenominer.processing.data.pubmeddtd.Sc;
import org.nii.phenominer.processing.data.pubmeddtd.Sec;
import org.nii.phenominer.processing.data.pubmeddtd.Sub;
import org.nii.phenominer.processing.data.pubmeddtd.Sup;
import org.nii.phenominer.processing.data.pubmeddtd.Title;
import org.nii.phenominer.processing.data.pubmeddtd.Xref;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class PMCXmlJAXBHelper {
	public static String extractText(Article article) {

		StringBuilder sb = new StringBuilder();

		List<Abstract> abstracts = article.getFront().getArticleMeta().getAbstract();
		for (Abstract abstrct : abstracts) {
			for (Sec sec : abstrct.getSec()) {
				processTextContent(sec.getAddressOrAlternativesOrArray(), sb, true);
			}
		}
		sb.append('\n');

		// extract text
		Body body = article.getBody();
		if (body != null) {
			for (Sec sec : body.getSec()) {

				Title title = sec.getTitle();
				if (title != null && title.getContent() != null) {
					processTextContent(title.getContent(), sb, true);
					sb.append('\n');
				}
				processTextContent(sec.getAddressOrAlternativesOrArray(), sb, false);
			}
		}
		return removeNoise(sb.toString());
	}

	private static String removeNoise(String s) {
		return s.replace("   ", " ").replace("  ", " ")//
				.replace(" , ", " ").replace(" ( )", "")//
				.replace("&#x000a0;", " ").replace(" ", " ")// nbsp
				.replace("[ – ]", " ").replace("[ ]", "");//
	}

	private static void processTextContent(List<Object> contents, StringBuilder sb, boolean addSpace) {

		for (Object o : contents) {
			if (o instanceof P) {
				P p = (P) o;
				processTextContent(p.getContent(), sb, true);
				sb.append('\n');

			} else if (o instanceof Italic) {
				Italic i = (Italic) o;
				processTextContent(i.getContent(), sb, true);
			} else if (o instanceof Bold) {
				Bold b = (Bold) o;
				processTextContent(b.getContent(), sb, true);
			} else if (o instanceof Monospace) {
				Monospace m = (Monospace) o;
				processTextContent(m.getContent(), sb, true);

			} else if (o instanceof Sup) {
				Sup s = (Sup) o;
				processTextContent(s.getContent(), sb, false);
			} else if (o instanceof Sub) {
				Sub s = (Sub) o;
				processTextContent(s.getContent(), sb, false);
			} else if (o instanceof Sc) {// SmallCapital
				Sc s = (Sc) o;
				processTextContent(s.getContent(), sb, false);

			} else if (o instanceof Xref) {

			} else if (o instanceof String) {
				sb.append(o);
				if (addSpace)
					sb.append(' ');
			}
		}
	}

	public static Integer extractPmid(Article article) {
		try {
			List<ArticleId> ids = article.getFront().getArticleMeta().getArticleId();
			for (ArticleId id : ids) {
				if ("pmid".equals(id.getPubIdType())) {
					return parseInt(id.getContent());
				}
			}
			if (!ids.isEmpty() && isNumeric(ids.get(0).getContent())) {
				return parseInt(ids.get(0).getContent());
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		return null;
	}

	public static String extractDoi(Article article) {
		try {
			List<ArticleId> ids = article.getFront().getArticleMeta().getArticleId();
			for (ArticleId id : ids) {
				if ("doi".equals(id.getPubIdType())) {
					return id.getContent();
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}
}
