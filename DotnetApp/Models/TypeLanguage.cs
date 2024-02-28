
namespace Models;

using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;


[Table("type_language")]
public class TypeLanguage {
    /// Field
    [Key]
    [Column("id_type_language")]
    public int IdTypeLanguage { get; set; }
    
    [Column("type_language_name")]
    public string TypeLanguageName { get; set; }
    
    [Column("status")]
    public int Status { get; set; }
    
    
    
    /// Constructor
    public TypeLanguage(){}
    
    
    /// Getter and setter
    
}
