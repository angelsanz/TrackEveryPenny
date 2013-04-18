package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.Conveniences;
import ca.jbrains.upfp.model.Transaction;
import ca.jbrains.upfp.test.ObjectMother;
import ca.jbrains.upfp.view.CsvFormat;
import ca.jbrains.upfp.view.CsvHeaderFormat;
import ca.jbrains.upfp.view.TransactionsCsvFileFormat;
import com.google.common.collect.Lists;
import org.jmock.*;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class FormatTransactionsAsCsvFileTest {
  private Mockery mockery = new Mockery();

  private final CsvFormat<Transaction> transactionCsvFormat
      = mockery.mock(CsvFormat.class, "transaction format");
  private final CsvHeaderFormat csvHeaderFormat = mockery
      .mock(
          CsvHeaderFormat.class, "header format");
  private final TransactionsCsvFileFormat
      transactionsCsvFileFormat
      = new TransactionsCsvFileFormat(
      csvHeaderFormat, transactionCsvFormat);

  @Test
  public void noTransactions() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(csvHeaderFormat).formatHeader();
          will(returnValue("::header::"));
        }});

    final String text = transactionsCsvFileFormat.format(
        Collections.<Transaction>emptyList());
    final List<String> lines = Arrays.asList(
        text.split(
            Conveniences.NEWLINE));
    assertEquals(1, lines.size());
    assertThat(lines.get(0), matches("::header::"));
    assertThat(text, endsWith(Conveniences.NEWLINE));
  }

  @Test
  public void aFewTransactions() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(csvHeaderFormat).formatHeader();
          will(returnValue("::header::"));
          allowing(transactionCsvFormat).format(
              with(
                  any(
                      Transaction.class)));
          will(
              onConsecutiveCalls(
                  returnValue("::row 1::"), returnValue(
                  "::row 2::"), returnValue("::row 3::")));
        }});

    final String text = transactionsCsvFileFormat.format(
        Lists.newArrayList(
            ObjectMother.createAnyNonNullTransaction(),
            ObjectMother.createAnyNonNullTransaction(),
            ObjectMother.createAnyNonNullTransaction()));

    final List<String> lines = Arrays.asList(
        text.split(
            Conveniences.NEWLINE));
    assertEquals(4, lines.size());
    assertThat(lines.get(0), matches("::header::"));
    assertThat(lines.get(1), matches("::row 1::"));
    assertThat(lines.get(2), matches("::row 2::"));
    assertThat(lines.get(3), matches("::row 3::"));
    assertThat(text, endsWith(Conveniences.NEWLINE));
  }

}
