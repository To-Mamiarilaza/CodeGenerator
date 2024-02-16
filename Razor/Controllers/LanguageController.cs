
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;

[ApiController]
[Route("/languages")]
public class LanguageController : ControllerBase {
    
    private readonly DatabaseContext _context;
    
    public LanguageController(DatabaseContext context) {
        _context = context;
    }
    
    [HttpGet]
    public async Task<ActionResult<IEnumerable<Language>>> GetAllLanguages() {
        return await _context.Languages.Include(l => l.TypeLanguage).Include(l => l.Pays).ToListAsync();
    }
    
    [HttpGet("{idLanguage}")]
    public async Task<ActionResult<Language>> GetLanguageById(int idLanguage) {
        var language = await _context.Languages.Include(l => l.TypeLanguage).Include(l => l.Pays).FirstOrDefaultAsync(l => l.IdLanguage == idLanguage);
        
        if(language == null) {
            return NotFound();
        }
        
        return language;
    }
    
    [HttpPost]
    public async Task<ActionResult<Language>> CreateLanguage(Language language) {
        _context.Entry(language.TypeLanguage).State = EntityState.Unchanged;
        _context.Entry(language.Pays).State = EntityState.Unchanged;
        _context.Languages.Add(language);
        await _context.SaveChangesAsync();
        
        return CreatedAtAction(nameof(GetLanguageById), new { idLanguage = language.IdLanguage }, language);
    }
    
    [HttpPut("{idLanguage}")]
    public async Task<IActionResult> UpdateLanguage(int idLanguage, Language language) {
        if (idLanguage != language.IdLanguage)
        {
            return BadRequest();
        }
        
        _context.Entry(language.TypeLanguage).State = EntityState.Unchanged;
        _context.Entry(language.Pays).State = EntityState.Unchanged;
        _context.Entry(language).State = EntityState.Modified;
        
        try
        {
            await _context.SaveChangesAsync();
        }
        catch(DbUpdateConcurrencyException)
        {
            throw;
        }
        
        return NoContent();
    }
    
    [HttpDelete("{idLanguage}")]
    public async Task<IActionResult> DeleteLanguage(int idLanguage) {
        var language = await _context.Languages.FindAsync(idLanguage);
        if (language == null)
        {
            return NotFound();
        }
        
        _context.Languages.Remove(language);
        await _context.SaveChangesAsync();
        
        return NoContent();
    }
    
}
