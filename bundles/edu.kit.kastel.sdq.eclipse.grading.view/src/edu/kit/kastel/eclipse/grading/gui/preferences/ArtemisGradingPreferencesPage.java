package edu.kit.kastel.eclipse.grading.gui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import edu.kit.kastel.eclipse.grading.gui.activator.Activator;
import edu.kit.kastel.sdq.eclipse.grading.api.PreferenceConstants;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 * <p>
 */

public class ArtemisGradingPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private BooleanFieldEditor isRelativeConfigPath;
	private StringFieldEditor relativeConfigPath;
	private FileFieldEditor absoluteConfigPath;
	private StringFieldEditor artemisUrl;
	private StringFieldEditor artemisUser;
	private StringFieldEditor artemisPassword;
	private StringFieldEditor configNameField;

	public ArtemisGradingPreferencesPage() {
		super(FieldEditorPreferencePage.GRID);
		this.setPreferenceStore(Activator.getDefault().getPreferenceStore());
		this.setDescription("Set preferences for the Artemis Grading");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		this.absoluteConfigPath = new FileFieldEditor(PreferenceConstants.P_ABSOLUTE_CONFIG_PATH,
				"Absolute config path: ", this.getFieldEditorParent());

		this.isRelativeConfigPath = new BooleanFieldEditor(PreferenceConstants.P_IS_RELATIVE_CONFIG_PATH,
				"Use relative config path", this.getFieldEditorParent());

		this.relativeConfigPath = new StringFieldEditor(PreferenceConstants.P_RELATIVE_CONFIG_PATH,
				"Relative config path: ", this.getFieldEditorParent());

		this.artemisUrl = new StringFieldEditor(PreferenceConstants.P_ARTEMIS_URL, "Artemis URL: ",
				this.getFieldEditorParent());

		this.artemisUser = new StringFieldEditor(PreferenceConstants.P_ARTEMIS_USER, "Artemis username: ",
				this.getFieldEditorParent());

		this.artemisPassword = new StringFieldEditor(PreferenceConstants.P_ARTEMIS_PASSWORD, "Artemis password: ",
				this.getFieldEditorParent());

		this.configNameField = new StringFieldEditor(PreferenceConstants.P_CONFIG_NAME, "Config name: ",
				this.getFieldEditorParent());

		this.addField(this.absoluteConfigPath);
		this.addField(this.configNameField);
		this.addField(this.relativeConfigPath);
		this.addField(this.isRelativeConfigPath);
		this.addField(this.artemisUrl);
		this.addField(this.artemisUser);
		this.addField(this.artemisPassword);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected void initialize() {
		super.initialize();
		this.relativeConfigPath.setEnabled(false, this.getFieldEditorParent());
		this.isRelativeConfigPath.setPropertyChangeListener(event -> {
			final boolean isRelative = ((Boolean) event.getNewValue()).booleanValue();
			if (isRelative) {
				this.relativeConfigPath.setEnabled(true, this.getFieldEditorParent());
				this.absoluteConfigPath.setEnabled(false, this.getFieldEditorParent());
			} else {
				this.relativeConfigPath.setEnabled(false, this.getFieldEditorParent());
				this.absoluteConfigPath.setEnabled(true, this.getFieldEditorParent());
			}
		});

		final boolean isRelativeSelected = this.isRelativeConfigPath.getBooleanValue();
		if (isRelativeSelected) {
			this.absoluteConfigPath.setEnabled(false, this.getFieldEditorParent());
		}

	}

}