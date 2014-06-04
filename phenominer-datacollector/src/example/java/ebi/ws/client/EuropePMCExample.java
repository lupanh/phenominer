package ebi.ws.client;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.activation.DataHandler;

public class EuropePMCExample {

	public static void main(String[] args) throws InterruptedException, QueryException_Exception {
		WSCitationImplService wSCitationImplService = new WSCitationImplService();
		WSCitationImpl wSCitationImpl = wSCitationImplService.getWSCitationImplPort();
		// Add your code to call the desired methods.
		new EuropePMCExample().runAllMethods(wSCitationImpl);
	}

	protected void runAllMethods(WSCitationImpl wSCitationImpl) throws QueryException_Exception {
		String query = "heart src:pmc";
		// String query = "TITLE:blood";
		String dataSet = "metadata";
		int offSet = 0;
		String resultType = "core";
		boolean synonym = false;
		String email = "vutranmai@gmail.com";
		// String database = "UNIPROT";
		// ResponseWrapper searchResult;

		ResponseWrapper resultsBean = wSCitationImpl.searchPublications(query, dataSet, resultType,
				offSet, synonym, email);
		List<Result> beanCollection = resultsBean.getResultList().getResult();
		for (Result result : beanCollection) {
			System.out.println(result.getPmcid() + "\t" + result.getPmid());
			System.out.println(result.getAbstractText());
			System.out.println(result.getIsOpenAccess());
			System.out.println(result.getFullTextUrlList().getFullTextUrl().get(1).getUrl());
			//Result fulltextXML = wSCitationImpl.getFulltextXML(result.getPmid(), "MED", email);
			//System.out.println(fulltextXML.getFullText());
			System.out.println("=======================================");
		}

	}

	private void printResultBean(Result citation) {
		// writeToOutput("score: " + resultBean.getScore());
		// Citation citation = resultBean.getCitation();
		writeToOutput(", title: " + citation.getTitle());
		writeToOutput(", is open access: " + citation.getIsOpenAccess());
		DataHandler dataHandler = citation.getFullText();
		try {
			if (dataHandler != null) {
				writeToOutputln("");
				// dataHandler.writeTo(System.out);
				dataHandler.getInputStream().close();
				writeToOutputln("");
				writeToOutputln("");
			}
		} catch (Exception e) {
		}
	}

	private void printSearchTerms(List<SearchTerm> searchTerms) {
		for (SearchTerm searchTerm : searchTerms) {
			writeToOutput(searchTerm.getTerm());
			List<String> dataSets = searchTerm.getDataSets();
			for (String dataSet : dataSets) {
				writeToOutput("\t" + dataSet);
			}
			writeToOutputln("");
		}
	}

	private void printFirstCitedByData(ResponseWrapper searchResult) {
		writeToOutput("");
		writeToOutputln("Printing Cited By Data");
		writeToOutput(searchResult.getCitationList().getCitation().get(0).getTitle());
	}

	private void printFirstDbCrossReference(ResponseWrapper searchResult) {
		writeToOutput("");
		writeToOutputln("Printing DbCrossReference Data");
		DbCrossReference dbCrossReference = searchResult.getDbCrossReferenceList()
				.getDbCrossReference().get(0);
		DbCrossReferenceInfo dbCrossReferenceInfo = dbCrossReference.getDbCrossReferenceInfo().get(
				0);
		writeToOutputln(dbCrossReference.getDbName() + ": " + dbCrossReferenceInfo.getInfo1()
				+ ", " + dbCrossReferenceInfo.getInfo2() + ", " + dbCrossReferenceInfo.getInfo3()
				+ ", " + dbCrossReferenceInfo.getInfo4() + ", ");
	}

	private void saveSupplementaryFiles(Result citation) {
		DataHandler dataHandler = citation.getSupplementaryFiles();
		if (dataHandler != null) {
			writeToOutputln("Saving zip file");
			File file = new File(citation.getPmcid() + ".zip");
			try {
				FileOutputStream out = new FileOutputStream(file);
				dataHandler.writeTo(out);
				dataHandler.getInputStream().close();
				out.close();
				writeToOutputln("Saved zip file in " + file.getAbsoluteFile());
			} catch (Exception e) {
			}
		}
	}

	private void printFirstReference(ResponseWrapper searchResult) {
		writeToOutput("");
		writeToOutputln("Printing Reference");
		try {
			writeToOutputln(searchResult.getReferenceList().getReference().get(0).getTitle());
		} catch (Exception e) {
		}
	}

	private void printFirstMinedTerm(ResponseWrapper searchResult) {
		writeToOutput("");
		writeToOutputln("Printing Mined Terms");
		try {
			writeToOutputln(searchResult.getSemanticTypeList().getSemanticType().get(0)
					.getTmSummary().get(0).getTerm());
		} catch (Exception e) {
		}
	}

	private void printFirstProfile(ResponseWrapper profileBean) {
		writeToOutput("");
		writeToOutputln("Printing Profile");
		try {
			writeToOutputln(profileBean.getProfileList().getPubType().get(0).getName());
		} catch (Exception e) {
		}
	}

	private void saveFullText(Result citation) {
		DataHandler dataHandler = citation.getFullText();
		if (dataHandler != null) {
			writeToOutputln("");
			writeToOutputln("Saving Full text");
			try {
				File file = new File(citation.getPmcid() + ".xml");
				FileOutputStream out = new FileOutputStream(file);
				dataHandler.writeTo(out);
				dataHandler.getInputStream().close();
				out.close();
				writeToOutputln("Saved xml file in " + file.getAbsoluteFile());
			} catch (Exception e) {
			}
		}
	}

	void writeToOutputln(String param) {
		System.out.println(param);
	}

	void writeToOutput(String param) {
		System.out.print(param);
	}

}
