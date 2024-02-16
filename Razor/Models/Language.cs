
namespace Models;

using Models;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;


[Table("language")]
public class Language {
    /// Field
    [Key]
    [Column("id_language")]
    public int IdLanguage { get; set; }
    
    [Column("language_name")]
    public string LanguageName { get; set; }
    
    [Column("etat")]
    public int Etat { get; set; }
    
    [ForeignKey("id_type_language")]
    public TypeLanguage TypeLanguage { get; set; }
    
    [ForeignKey("id_pays")]
    public Pays Pays { get; set; }
    
    
    
    /// Constructor
    public Language(){}
    
    
    /// Getter and setter
    
}
