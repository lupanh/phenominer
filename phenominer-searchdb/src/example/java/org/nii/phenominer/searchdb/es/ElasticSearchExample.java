package org.nii.phenominer.searchdb.es;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.nii.phenominer.searchdb.data.phenominer.AnnotationCollection;
import org.nii.phenominer.searchdb.data.phenominer.Disorder;
import org.nii.phenominer.searchdb.data.phenominer.Term;
import org.nii.phenominer.searchdb.parser.S5XMLParser;

import com.google.gson.Gson;

import static org.elasticsearch.client.Requests.createIndexRequest;

public class ElasticSearchExample {
	private Client client;
	private Node node;

	public ElasticSearchExample() {
		node = new NodeBuilder().node();
		client = node.client();
	}

	@SuppressWarnings("resource")
	public ElasticSearchExample(String ipAddress) {
		client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(
				ipAddress, 9300));
	}

	public void closeConnection() {
		client.close();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void createIndexResponse(String indexname, String type, List<String> jsondata) {
		IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type).setRefresh(true);

		for (int i = 0; i < jsondata.size(); i++) {
			requestBuilder.setSource(jsondata.get(i)).execute().actionGet();
		}
	}

	public IndexResponse createIndexResponse(String indexname, String type, String jsondata) {
		IndexResponse response = client.prepareIndex(indexname, type).setSource(jsondata).execute()
				.actionGet();
		return response;
	}

	public DeleteByQueryResponse deleteAll(String indexname, String type) {
		DeleteByQueryResponse response = client.prepareDeleteByQuery(indexname)
				.setQuery(QueryBuilders.termQuery("_type", type)).execute().actionGet();
		return response;
	}

	public void setMapping(String indexname, String type) throws Exception {
		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject(type)
				.startObject("properties").startObject("link.ontology").field("type", "string")
				.field("store", "yes").field("index", "not_analyzed").endObject()
				.startObject("associatedDisorders.disorder.name").field("type", "string")
				.field("store", "yes").field("index", "not_analyzed").endObject()
				.startObject("associatedDisorders.disorder.omimId").field("type", "string")
				.field("store", "yes").field("index", "not_analyzed").endObject()
				.startObject("associatedDisorders.disorder.pval").field("type", "double")
				.field("store", "yes").endObject().endObject().endObject().endObject();
		client.admin().indices().preparePutMapping(indexname).setType(type).setSource(builder)
				.execute().actionGet();
	}

	public void deleteMapping(String indexname, String type) {
		client.admin().indices().prepareDeleteMapping(indexname).setType(type).execute();
	}

	public void createMapping(String indexname, String type) throws Exception {
		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject(type)
				.startObject("properties").startObject("link.ontology").field("type", "string")
				.field("store", "yes").field("index", "not_analyzed").endObject()
				.startObject("associatedDisorders.disorder.name").field("type", "string")
				.field("store", "yes").field("index", "not_analyzed").endObject()
				.startObject("associatedDisorders.disorder.omimId").field("type", "string")
				.field("store", "yes").field("index", "not_analyzed").endObject()
				.startObject("associatedDisorders.disorder.pval").field("type", "double")
				.field("store", "yes").endObject()
				.startObject("associatedDisorders.disorder.supp").field("type", "double")
				.field("store", "yes").endObject()	
				.startObject("associatedDisorders.disorder.conf").field("type", "double")
				.field("store", "yes").endObject()
				.startObject("associatedDisorders.disorder.lift").field("type", "double")
				.field("store", "yes").endObject()
				.endObject().endObject().endObject();
		client.admin().indices().create(createIndexRequest(indexname).mapping(type, builder))
				.actionGet();
	}

	public void deleteIndex(String indexname) {
		DeleteIndexResponse delete = client.admin().indices()
				.delete(new DeleteIndexRequest(indexname)).actionGet();
		if (!delete.isAcknowledged()) {
			System.out.println("Index wasn't deleted");
		}
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		ElasticSearchExample es = new ElasticSearchExample("23.92.53.181");
		//ElasticSearchExample es = new ElasticSearchExample("localhost");

		try {
			// es.deleteAll("phenominer", "phenotype");
			// es.deleteMapping("phenominer", "phenotype");
			es.deleteIndex("phenominer");
			es.createMapping("phenominer", "phenotype");

			AnnotationCollection collection = S5XMLParser.getAnnotationCollection("data/S5");
			for (Term term : collection.getTerm()) {
				List<Disorder> disorders = new ArrayList<Disorder>();
				for (Disorder disorder : term.getAssociatedDisorders().getDisorder()) {
					if (!disorder.getPval().equals("") && (disorder.getName() != null))
						disorders.add(disorder);
				}
				term.getAssociatedDisorders().setDisorder(disorders);
				String json = gson.toJson(term);
				es.createIndexResponse("phenominer", "phenotype", json);
			}
			es.closeConnection();
		} catch (Exception e) {
			System.err.println(e);
			es.closeConnection();
		}
	}
}