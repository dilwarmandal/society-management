package com.society.service.email;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.society.bo.TransactionDTO;
import com.society.dao.UtilityDao;
import com.society.entities.member.Member;
import com.society.entities.system.Statement;
import com.society.entities.user.AppUser;
import com.society.repositories.StatementRepository;
import com.society.service.MemberService;
import com.society.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@Service("emailService")
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    UtilityDao utilityDao;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StatementRepository statementRepository;

    @Value("${spring.mail.username}")
    private String email;

    @Value("${spring.mail.name}")
    private String name;

    public static DataSource generateMonthlyReport(List<TransactionDTO> transactionDTOS) {
        Document document = new Document(PageSize.A4, 30f, 30f, 30f, 30f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 1, 1, 2, 1, 1, 1});
            PdfPCell hcell;

            hcell = new PdfPCell(getHeaderCell("Date"));
            table.addCell(hcell);

            hcell = new PdfPCell(getHeaderCell("Ref. No"));
            table.addCell(hcell);

            hcell = new PdfPCell(getHeaderCell("Transaction Amount"));
            table.addCell(hcell);

            hcell = new PdfPCell(getHeaderCell("Transaction Type"));
            table.addCell(hcell);

            hcell = new PdfPCell(getHeaderCell("Transaction Selection"));
            table.addCell(hcell);

            hcell = new PdfPCell(getHeaderCell("Transaction Mode"));
            table.addCell(hcell);

            hcell = new PdfPCell(getHeaderCell("Transaction Desc"));
            table.addCell(hcell);
            for (TransactionDTO transactionDTO : transactionDTOS) {
                PdfPCell cell;

                cell = new PdfPCell(getNormalCell(transactionDTO.getTransDate().toString()));
                table.addCell(cell);

                cell = new PdfPCell(getNormalCell(String.valueOf(transactionDTO.getTransRef())));
                table.addCell(cell);

                cell = new PdfPCell(getNormalCell(transactionDTO.getAmount().toString()));
                table.addCell(cell);

                cell = new PdfPCell(getNormalCell(String.valueOf(transactionDTO.getTransType())));
                table.addCell(cell);

                cell = new PdfPCell(getNormalCell(String.valueOf(transactionDTO.getTransSelect())));
                table.addCell(cell);

                cell = new PdfPCell(getNormalCell(String.valueOf(transactionDTO.getTransMode())));
                table.addCell(cell);

                cell = new PdfPCell(getNormalCell(String.valueOf(transactionDTO.getDescription())));
                table.addCell(cell);
            }
            PdfWriter.getInstance(document, out);
            document.open();
            Paragraph preface = new Paragraph(StringUtils.capitalize("Monthly Statement - " + LocalDate.now().minusMonths(1).getMonth() + "," + LocalDate.now().minusMonths(1).getYear()),
                    FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLUE));
            preface.setAlignment(Element.ALIGN_CENTER);
            document.add(preface);
            table.setSpacingBefore(20);
            document.add(table);
            document.close();
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
        return new ByteArrayDataSource(out.toByteArray(), "application/pdf");
    }

    public static PdfPCell getNormalCell(String string)
            throws DocumentException, IOException {
        if (string != null && "".equals(string)) {
            return new PdfPCell();
        }
        Font f = FontFactory.getFont(FontFactory.COURIER, "Cp1250", true, 6);
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingRight(5);
        return cell;
    }

    public static PdfPCell getHeaderCell(String string)
            throws DocumentException, IOException {
        if (string != null && "".equals(string)) {
            return new PdfPCell();
        }
        Font f = FontFactory.getFont(FontFactory.COURIER_BOLD, "Cp1250", true, 6);
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingRight(5);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    @Override
    public void resetPassword(String contextPath, String token, AppUser appUser) {
        final String url = contextPath + "/changePassword?id=" + appUser.getId() + "&token=" + token;
        Context context = new Context();
        context.setVariable("resetMessage", "Please reset your passwordby clicking the link below.");
        context.setVariable("resetUrl", url);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(appUser.getEmail());
            messageHelper.setSubject("Reset Password");
            messageHelper.setFrom(new InternetAddress(email, name));
            String content = templateEngine.process("email/password-reset", context);
            messageHelper.setText(content, true);
        };
        try {
            sender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }

    @Override
    public void sentMonthlyStatement() {
        List<Member> members = memberService.findAllMembers();
        InternetAddress address[] = new InternetAddress[members.size()];
        LocalDate toLocalDate = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate fromLocalDate = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        Statement responseStatement = statementRepository.findStatementByMonthAndYear(toLocalDate.getMonth().getValue(), toLocalDate.getYear());
        if (responseStatement == null) {
            for (int i = 0; i < members.size(); i++) {
                try {
                    Member member = members.get(i);
                    if (member.getEmailSubscribe()) {
                        address[i] = new InternetAddress(member.getEmailId());
                    }
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
            if (address.length > 0) {
                Date fromDate = Date.from(fromLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date toDate = Date.from(toLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                List<TransactionDTO> transactionList = transactionService.findAllTransactionsBetweenDate(fromDate, toDate);

                DataSource dataSource = generateMonthlyReport(transactionList);
                String header = StringUtils.capitalize("Monthly Statement - " + toLocalDate.getMonth() + ", " + toLocalDate.getYear());

                Context context = new Context();
                context.setVariable("monthStatement", toLocalDate.getMonth() + ", " + toLocalDate.getYear());

                MimeMessage message = sender.createMimeMessage();
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                try {
                    String content = templateEngine.process("email/monthly-statement", context);

                    messageBodyPart.setText(header);
                    messageBodyPart.setHeader("Content-Transfer-Encoding", "base64");
                    messageBodyPart.setDataHandler(new DataHandler(dataSource));
                    messageBodyPart.setFileName(header + ".pdf");
                    multipart.addBodyPart(messageBodyPart);

                    attachmentBodyPart.setContent(content, "text/html");
                    multipart.addBodyPart(attachmentBodyPart);

                    message.setHeader("Content-Type", "multipart/mixed");
                    message.setRecipients(Message.RecipientType.TO, "dilwar.mandal@hotmail.com");
                    message.setRecipients(Message.RecipientType.BCC, address);
                    message.setSubject(header);
                    message.setFrom(new InternetAddress(email, name));
                    message.setSentDate(new Date());
                    message.setContent(multipart);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } finally {
                    Statement statement = new Statement();
                    statement.setMonth(toLocalDate.getMonth().getValue());
                    statement.setYear(toLocalDate.getYear());
                    statement.setLastUpdatedDate(new Date());
                    statement.setStatus(true);
                    Statement response = statementRepository.saveAndFlush(statement);
                    if (response.getStatementId() != null) {
                        sender.send(message);
                    }
                }
            }
        }
    }
}