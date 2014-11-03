package org.nii.phenominer.ml.crf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import opennlp.model.AbstractModel;
import opennlp.model.Event;
import opennlp.model.Sequence;
import opennlp.model.SequenceStream;
import opennlp.tools.namefind.NameContextGenerator;
import opennlp.tools.namefind.NameFinderEventStream;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

public class NERSequenceStream implements SequenceStream {

	private NameContextGenerator pcg;
	private List<NameSample> samples;

	public NERSequenceStream(ObjectStream<NameSample> psi) throws IOException {
		this(psi, new CRFContextGenerator((AdaptiveFeatureGenerator) null));
	}

	public NERSequenceStream(ObjectStream<NameSample> psi,
			AdaptiveFeatureGenerator featureGen) throws IOException {
		this(psi, new CRFContextGenerator(featureGen));
	}

	public NERSequenceStream(ObjectStream<NameSample> psi, NameContextGenerator pcg)
			throws IOException {
		samples = new ArrayList<NameSample>();

		NameSample sample;
		while ((sample = psi.read()) != null) {
			samples.add(sample);
		}

		System.err.println("Got " + samples.size() + " sequences");

		this.pcg = pcg;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Event[] updateContext(Sequence sequence, AbstractModel model) {
		Sequence<NameSample> pss = sequence;
		TokenNameFinder tagger = new NameFinderME(new TokenNameFinderModel("x-unspecified", model,
				Collections.<String, Object> emptyMap(), null));
		String[] sentence = pss.getSource().getSentence();
		String[] tags = NameFinderEventStream.generateOutcomes(tagger.find(sentence), null,
				sentence.length);
		Event[] events = new Event[sentence.length];

		NameFinderEventStream.generateEvents(sentence, tags, pcg).toArray(events);

		return events;
	}

	@SuppressWarnings("rawtypes")
	public Iterator<Sequence> iterator() {
		return new NameSampleSequenceIterator(samples.iterator(), pcg);
	}
}

@SuppressWarnings("rawtypes")
class NameSampleSequenceIterator implements Iterator<Sequence> {

	private Iterator<NameSample> psi;
	private NameContextGenerator cg;

	public NameSampleSequenceIterator(Iterator<NameSample> psi, NameContextGenerator pcg) {
		this.psi = psi;
		cg = pcg;
	}

	public boolean hasNext() {
		return psi.hasNext();
	}

	public Sequence<NameSample> next() {
		NameSample sample = psi.next();
		
		String sentence[] = sample.getSentence();
		String tags[] = NameFinderEventStream.generateOutcomes(sample.getNames(), null,
				sentence.length);
		Event[] events = new Event[sentence.length];

		for (int i = 0; i < sentence.length; i++) {

			// it is safe to pass the tags as previous tags because
			// the context generator does not look for non predicted tags
			String[] context = cg.getContext(i, sentence, tags, null);

			events[i] = new Event(tags[i], context);
		}
		Sequence<NameSample> sequence = new Sequence<NameSample>(events, sample);
		return sequence;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}