package gui.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

//classe utilitaria, q serve para RETORNAR o STAGE/PALCO atual
//ou SEJA em QUAL TELA esta aberta atualmente no software
public class Utils {

	// exemplo SE aperta em um BOTAO e para pegar o STAGE desse BOTAO...

	// funcao q retorna o STAGE/PALCO/TELA atual
	public static Stage currentStage(ActionEvent event) {
		// implementacao para pegar o STAGE a partir do OBJ de EVENT
		// pegando a SCENA e dps pegando a JANELA/WINDOW
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	// implementando o metodo TryParseToInt, q ajuda a converter o valor da caixinha
	// para um valor inteiro
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		// SE der ERRO caso o usuario passe um valor INVALIDO para buscar pelos ID
		catch (NumberFormatException e) {
			return null;
		}
	}
	
	
	
//implementando o metodo TryParseToDouble, q ajuda a converter o valor da caixinha
//para um valor double
		public static Double tryParseToDouble(String str) {
			try {
				return Double.parseDouble(str);
			}
			// SE der ERRO caso o usuario passe um valor INVALIDO para buscar pelos ID
			catch (NumberFormatException e) {
				return null;
			}
		}
	
	
	
	// metodo q o professor Nelio pediu para COPIAR e COLAR do material PDF
	// serve basicamente para ORGANIZAR a DATA... foi isso q o professor Nelio
	// falou
	// SERVE PARA A DATA DE NASCIMENTO birthDate do SELLER FICAR FORMATADA
	public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(format);

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return cell;
		});
	}

	// metodo q o professor Nelio pediu para COPIAR e COLAR do material PDF
	// serve basicamente para organizar os numeros de ponto flutuante +ou- isso
	//
	// SERVE PARA O BASYSALARY DO SELLER FICAR FORMATADO
	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%." + decimalPlaces + "f", item));
					}
				}
			};
			return cell;
		});
	}

	// metodo q o professor Nelio pediu para COPIAR e COLAR do material PDF
	// serve basicamente para organizar a DATA de nascimento dos SELLERS
	//
	public static void formatDatePicker(DatePicker datePicker, String format) {
		datePicker.setConverter(new StringConverter<LocalDate>() {

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
			{
				datePicker.setPromptText(format.toLowerCase());
			}

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

}
