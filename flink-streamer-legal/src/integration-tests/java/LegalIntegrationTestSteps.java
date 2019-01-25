import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.dineshgpillai.LegalDocumentSink;
import io.github.dineshgpillai.PartySink;
import io.github.dineshgpillai.StreamingJob;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


import java.io.File;


public class LegalIntegrationTestSteps {

    File sourcefile;

    @Given("the following file in this location contains the CSA data {string}")
    public void the_following_file_in_this_location_contains_the_CSA_data(String file) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        sourcefile = new File("src/integration-tests/resources/"+file);
        assert sourcefile != null;

    }

    @When("the streamer reads this file from source")
    public void the_streamer_reads_this_file_from_source() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // configure your test environment
        env.setParallelism(1);


        DataStream<String> dataStream;
        dataStream = env.readTextFile(sourcefile.getAbsolutePath());
        assert dataStream != null;

        // execute
        env.execute();


    }



    @Then("stores the legal documents to Hz which should contain {int} legal documents {int} Parties")
    public void stores_the_legal_documents_to_Hz_which_should_contain_legal_documents_Parties(int legaldocumentcount, int partycount) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // configure your test environment
        env.setParallelism(1);
        // values are collected in a static variable
        DataStream<String> dataStream;
        dataStream = env.readTextFile(sourcefile.getAbsolutePath());
        assert dataStream != null;

        LegalDocumentSink.values.clear();
        PartySink.values.clear();

        StreamingJob job = new StreamingJob();
        job.streamAndSink(dataStream, new LegalDocumentSink(), new PartySink());

        // execute
        env.execute();

        assert LegalDocumentSink.values.size()==legaldocumentcount;
        assert PartySink.values.size()==partycount;
    }


}
